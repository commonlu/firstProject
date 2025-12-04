package com.gdou.hr_system.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdou.hr_system.entity.Employee;
import com.gdou.hr_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:8081")
public class AIController {

    // 🔑 替换为你自己的阿里云百炼 API Key
    private static final String API_KEY = "sk-be87543ad3324a77b4a397f000564ba8";
    private static final String API_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";
    
    // 上下文管理：存储不同会话的聊天历史
    private static final ConcurrentHashMap<String, List<Map<String, String>>> CONTEXT_MAP = new ConcurrentHashMap<>();
    private static final int MAX_CONTEXT_LENGTH = 20; // 最大上下文长度

    @Autowired
    private EmployeeService employeeService; // ← 注入你的服务

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, Object> request) {
        String question = (String) request.get("question");
        String sessionId = (String) request.get("sessionId");
        
        // 如果没有sessionId，生成一个默认的
        if (sessionId == null || sessionId.trim().isEmpty()) {
            sessionId = "default";
        }

        if (question == null || question.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("answer", "问题不能为空"));
        }

        try {
            // 获取或创建会话上下文
            List<Map<String, String>> context = CONTEXT_MAP.computeIfAbsent(sessionId, k -> new ArrayList<>());
            
            // 添加当前问题到上下文
            context.add(Map.of("role", "user", "content", question));
            
            // 确保上下文长度不超过限制
            if (context.size() > MAX_CONTEXT_LENGTH) {
                context = context.subList(context.size() - MAX_CONTEXT_LENGTH, context.size());
                CONTEXT_MAP.put(sessionId, context);
            }
            
            // 🧠 意图识别：是否是日期时间类问题？
            if (isDateTimeQuestion(question)) {
                String answer = handleDateTimeQuestion(question);
                context.add(Map.of("role", "assistant", "content", answer));
                return ResponseEntity.ok(Map.of("answer", answer));
            }

            // 🧠 意图识别：是否是员工数据操作类问题？

            // 1. 查员工（按姓名）
            if (question.contains("查") && (question.contains("员工") || question.contains("谁"))) {
                String name = extractNameFromQuestion(question);
                if (name != null) {
                    Employee condition = new Employee();
                    condition.setName(name); // 设置搜索条件
                    List<Employee> employees = employeeService.searchEmployee(condition);
                    if (!employees.isEmpty()) {
                        Employee emp = employees.get(0); // 取第一个匹配项
                        String status = emp.getStatus() == 1 ? "在职" : "离职";
                        String answer = String.format("✅ 员工【%s】\n- 状态：%s\n- 手机号：%s\n- 部门编号：%d\n- 员工编号：%s",
                                emp.getName(), status, emp.getPhone(), emp.getDeptId(), emp.getCode());
                        context.add(Map.of("role", "assistant", "content", answer));
                        return ResponseEntity.ok(Map.of("answer", answer));
                    } else {
                        String answer = "❌ 未找到名为【" + name + "】的员工。";
                        context.add(Map.of("role", "assistant", "content", answer));
                        return ResponseEntity.ok(Map.of("answer", answer));
                    }
                }
            }

            // 2. 员工总数
            if (question.contains("多少员工") || question.contains("员工总数") || question.contains("一共有")) {
                int count = employeeService.countEmployees(); // ← 用你的真实方法名
                String answer = "📊 当前系统共有 " + count + " 名员工。";
                context.add(Map.of("role", "assistant", "content", answer));
                return ResponseEntity.ok(Map.of("answer", answer));
            }

            // 3. 在职/离职统计
            if (question.contains("统计") || question.contains("分析") || question.contains("分布")) {
                int 在职 = employeeService.countActiveEmployees();     // ← 用你的真实方法名
                int 离职 = employeeService.countInactiveEmployees();  // ← 用你的真实方法名
                String answer = String.format("📊 员工状态分布：\n- 在职：%d 人\n- 离职：%d 人\n- 总计：%d 人",
                        在职, 离职, 在职 + 离职);
                context.add(Map.of("role", "assistant", "content", answer));
                return ResponseEntity.ok(Map.of("answer", answer));
            }

            // 4. 性别统计
            if (question.contains("男女") || question.contains("性别分布") || question.contains("男女人数")) {
                int 男性 = employeeService.getGenderMan();     // ← 用你的真实方法名
                int 女性 = employeeService.getGenderWomen();   // ← 用你的真实方法名
                String answer = String.format("👫 性别分布：\n- 男性：%d 人\n- 女性：%d 人\n- 总计：%d 人",
                        男性, 女性, 男性 + 女性);
                context.add(Map.of("role", "assistant", "content", answer));
                return ResponseEntity.ok(Map.of("answer", answer));
            }

            // 5. 导出引导
            if (question.contains("导出") && question.contains("员工")) {
                String answer = "📥 请点击页面右上角的【数据导出】按钮，系统将自动生成 Excel 文件供您下载。";
                context.add(Map.of("role", "assistant", "content", answer));
                return ResponseEntity.ok(Map.of("answer", answer));
            }

            // 6. 新增引导
            if (question.contains("新增") && (question.contains("员工") || question.contains("添加"))) {
                String answer = "➕ 请点击页面右上角的【新增员工】按钮，填写表单后点击【提交】即可完成新增。";
                context.add(Map.of("role", "assistant", "content", answer));
                return ResponseEntity.ok(Map.of("answer", answer));
            }

            // ❓ 7. 其他问题 → 调用通义千问，并传递上下文
            return callQwenAPI(question, context, sessionId);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("answer", "系统内部错误，请稍后再试。"));
        }
    }
    
    // 清空上下文API
    @PostMapping("/clearContext")
    public ResponseEntity<Map<String, String>> clearContext(@RequestBody Map<String, Object> request) {
        String sessionId = (String) request.get("sessionId");
        
        if (sessionId == null || sessionId.trim().isEmpty()) {
            sessionId = "default";
        }
        
        CONTEXT_MAP.remove(sessionId);
        return ResponseEntity.ok(Map.of("answer", "上下文已清空"));
    }

    // 🧩 从问题中提取姓名（简单中文名提取）
    private String extractNameFromQuestion(String question) {
        String[] prefixes = {"查一下", "查询", "找一下", "谁是", "看看", "查", "找"};
        for (String prefix : prefixes) {
            if (question.contains(prefix)) {
                int start = question.indexOf(prefix) + prefix.length();
                String rest = question.substring(start).trim();
                StringBuilder name = new StringBuilder();
                for (char c : rest.toCharArray()) {
                    if (name.length() >= 3) break;
                    if (c >= '\u4e00' && c <= '\u9fa5') { // 中文字符
                        name.append(c);
                    } else if (!name.isEmpty()) {
                        break; // 遇到非中文就停止
                    }
                }
                return name.length() > 0 ? name.toString() : null;
            }
        }
        return null;
    }
    
    // 🧩 判断是否是日期时间类问题
    private boolean isDateTimeQuestion(String question) {
        return question.contains("今天") || question.contains("现在") || 
               question.contains("几号") || question.contains("几点") || 
               question.contains("星期") || question.contains("日期") || 
               question.contains("时间") || question.contains("年月日");
    }
    
    // 🧩 处理日期时间类问题
    private String handleDateTimeQuestion(String question) {
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalTime time = now.toLocalTime();
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        
        String weekDay = getWeekDay(today.getDayOfWeek().getValue());
        
        if (question.contains("现在几点") || question.contains("现在时间")) {
            return String.format("🕒 当前时间是：%s", time.format(timeFormatter));
        } else if (question.contains("今天几号") || question.contains("今天日期")) {
            return String.format("📅 今天是：%s %s", today.format(dateFormatter), weekDay);
        } else if (question.contains("今天星期") || question.contains("今天周几")) {
            return String.format("📅 今天是%s", weekDay);
        } else {
            return String.format("📅 当前时间是：%s %s", now.format(fullFormatter), weekDay);
        }
    }
    
    // 🧩 获取星期几
    private String getWeekDay(int dayOfWeek) {
        String[] weekdays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weekdays[dayOfWeek % 7];
    }

    // 🧩 调用通义千问 API（支持上下文）
    private ResponseEntity<Map<String, String>> callQwenAPI(String question, List<Map<String, String>> context, String sessionId) throws Exception {
        // 构建上下文JSON
        StringBuilder messagesBuilder = new StringBuilder();
        for (Map<String, String> message : context) {
            if (messagesBuilder.length() > 0) {
                messagesBuilder.append(",");
            }
            messagesBuilder.append(String.format("{\"role\": \"%s\", \"content\": \"%s\"}",
                    message.get("role"), message.get("content").replace("\"", "\\\"")));
        }
        
        String jsonBody = String.format("""
            {
              "model": "qwen-turbo",
              "input": {
                "messages": [%s]
              }
            }
            """, messagesBuilder.toString());

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            String answer = root.path("output").path("text").asText("抱歉，我暂时无法回答这个问题。");
            
            // 将AI回答添加到上下文
            List<Map<String, String>> updatedContext = CONTEXT_MAP.get(sessionId);
            if (updatedContext != null) {
                updatedContext.add(Map.of("role", "assistant", "content", answer));
            }
            
            return ResponseEntity.ok(Map.of("answer", answer));
        } else {
            return ResponseEntity.status(500).body(Map.of("answer", "AI 服务返回错误，请检查网络或API密钥。"));
        }
    }
}