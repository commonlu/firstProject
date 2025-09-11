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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:8081")
public class AIController {

    // 🔑 替换为你自己的阿里云百炼 API Key
    private static final String API_KEY = "sk-be87543ad3324a77b4a397f000564ba8";
    private static final String API_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";

    @Autowired
    private EmployeeService employeeService; // ← 注入你的服务

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, Object> request) {
        String question = (String) request.get("question");

        if (question == null || question.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("answer", "问题不能为空"));
        }

        try {
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
                        return ResponseEntity.ok(Map.of("answer",
                                String.format("✅ 员工【%s】\n- 状态：%s\n- 手机号：%s\n- 部门编号：%d\n- 员工编号：%s",
                                        emp.getName(), status, emp.getPhone(), emp.getDeptId(), emp.getCode())));
                    } else {
                        return ResponseEntity.ok(Map.of("answer", "❌ 未找到名为【" + name + "】的员工。"));
                    }
                }
            }

            // 2. 员工总数
            if (question.contains("多少员工") || question.contains("员工总数") || question.contains("一共有")) {
                int count = employeeService.countEmployees(); // ← 用你的真实方法名
                return ResponseEntity.ok(Map.of("answer", "📊 当前系统共有 " + count + " 名员工。"));
            }

            // 3. 在职/离职统计
            if (question.contains("统计") || question.contains("分析") || question.contains("分布")) {
                int 在职 = employeeService.countActiveEmployees();     // ← 用你的真实方法名
                int 离职 = employeeService.countInactiveEmployees();  // ← 用你的真实方法名
                return ResponseEntity.ok(Map.of("answer",
                        String.format("📊 员工状态分布：\n- 在职：%d 人\n- 离职：%d 人\n- 总计：%d 人",
                                在职, 离职, 在职 + 离职)));
            }

            // 4. 性别统计
            if (question.contains("男女") || question.contains("性别分布") || question.contains("男女人数")) {
                int 男性 = employeeService.getGenderMan();     // ← 用你的真实方法名
                int 女性 = employeeService.getGenderWomen();   // ← 用你的真实方法名
                return ResponseEntity.ok(Map.of("answer",
                        String.format("👫 性别分布：\n- 男性：%d 人\n- 女性：%d 人\n- 总计：%d 人",
                                男性, 女性, 男性 + 女性)));
            }

            // 5. 导出引导
            if (question.contains("导出") && question.contains("员工")) {
                return ResponseEntity.ok(Map.of("answer",
                        "📥 请点击页面右上角的【数据导出】按钮，系统将自动生成 Excel 文件供您下载。"));
            }

            // 6. 新增引导
            if (question.contains("新增") && (question.contains("员工") || question.contains("添加"))) {
                return ResponseEntity.ok(Map.of("answer",
                        "➕ 请点击页面右上角的【新增员工】按钮，填写表单后点击【提交】即可完成新增。"));
            }

            // ❓ 7. 其他问题 → 调用通义千问
            return callQwenAPI(question);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("answer", "系统内部错误，请稍后再试。"));
        }
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

    // 🧩 调用通义千问 API（封装原逻辑）
    private ResponseEntity<Map<String, String>> callQwenAPI(String question) throws Exception {
        String jsonBody = String.format("""
            {
              "model": "qwen-turbo",
              "input": {
                "messages": [
                  {"role": "user", "content": "%s"}
                ]
              }
            }
            """, question.replace("\"", "\\\""));

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
            return ResponseEntity.ok(Map.of("answer", answer));
        } else {
            return ResponseEntity.status(500).body(Map.of("answer", "AI 服务返回错误，请检查网络或API密钥。"));
        }
    }
}