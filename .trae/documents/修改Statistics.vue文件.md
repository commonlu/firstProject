# 修改Statistics.vue文件

## 任务概述
将"薪资结构分析"部分移动到"核心人力指标"下面，并修改为显示所有员工信息的自动滚动列表。

## 实现步骤

### 1. 调整HTML结构
- 将薪资结构分析部分（第119-141行）移动到核心人力指标部分（第67行）之后
- 修改薪资结构分析的标题和内容，改为显示员工信息

### 2. 修改数据获取逻辑
- 添加员工列表数据属性
- 添加获取所有员工信息的方法
- 在fetchAllStats方法中调用获取员工信息的方法

### 3. 实现员工信息滚动列表
- 修改薪资结构分析部分的HTML，改为显示员工列表
- 添加CSS样式实现垂直滚动效果
- 添加JavaScript逻辑实现自动缓慢滚动

### 4. 优化样式和交互
- 调整员工列表的样式，使其美观易读
- 添加悬停效果，方便查看详细信息
- 实现滚动暂停/恢复功能

## 具体修改内容

### HTML结构调整
```html
<!-- 将薪资结构分析部分移动到核心人力指标下面 -->
<!-- 核心人力指标部分 -->
<!-- ... -->

<!-- 员工信息滚动列表（原薪资结构分析部分） -->
<div class="stats-section">
  <h3 class="section-title">员工信息</h3>
  <div class="section-subtitle">所有员工信息列表</div>
  <div class="employee-scroll-container">
    <div class="employee-list" ref="employeeList">
      <div class="employee-item" v-for="employee in employees" :key="employee.id">
        <div class="employee-info">
          <div class="employee-name">{{ employee.name }}</div>
          <div class="employee-details">
            <span class="employee-dept">{{ employee.deptName }}</span>
            <span class="employee-position">{{ employee.position }}</span>
            <span class="employee-phone">{{ employee.phone }}</span>
          </div>
        </div>
        <div class="employee-status" :class="{ 'active': employee.status === 1, 'inactive': employee.status === 0 }">
          {{ employee.status === 1 ? '在职' : '离职' }}
        </div>
      </div>
    </div>
  </div>
</div>

<!-- 请假统计分析部分 -->
<!-- ... -->

<!-- 文件统计部分 -->
<!-- ... -->
```

### 数据和方法修改
```javascript
data() {
  return {
    // ... 现有数据 ...
    employees: [], // 员工列表数据
    scrollInterval: null, // 滚动定时器
    scrollSpeed: 1, // 滚动速度
  };
},
methods: {
  async fetchAllStats() {
    // ... 现有代码 ...
    // 获取所有员工信息
    await this.fetchEmployees();
    // ... 现有代码 ...
  },
  
  async fetchEmployees() {
    try {
      const response = await axios.get('/api/employees');
      this.employees = response.data;
      // 启动自动滚动
      this.startAutoScroll();
    } catch (error) {
      console.error('获取员工数据失败:', error);
    }
  },
  
  startAutoScroll() {
    const container = this.$refs.employeeList;
    if (!container) return;
    
    // 清除之前的定时器
    if (this.scrollInterval) {
      clearInterval(this.scrollInterval);
    }
    
    // 设置自动滚动定时器
    this.scrollInterval = setInterval(() => {
      container.scrollTop += this.scrollSpeed;
      
      // 滚动到顶部时重新开始
      if (container.scrollTop >= container.scrollHeight - container.clientHeight) {
        container.scrollTop = 0;
      }
    }, 50);
  },
  
  // 组件销毁时清除定时器
  beforeUnmount() {
    if (this.scrollInterval) {
      clearInterval(this.scrollInterval);
    }
  }
}
```

### CSS样式修改
```css
/* 员工信息滚动容器 */
.employee-scroll-container {
  padding: 20px;
  background: #f8f9fa;
  border-radius: 8px;
  height: 300px;
  overflow: hidden;
  position: relative;
}

/* 员工列表 */
.employee-list {
  height: 100%;
  overflow-y: auto;
  padding-right: 10px;
  /* 隐藏滚动条 */
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.employee-list::-webkit-scrollbar {
  display: none;
}

/* 员工项 */
.employee-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  background: white;
  border-radius: 8px;
  margin-bottom: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.employee-item:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

/* 员工信息 */
.employee-info {
  flex: 1;
}

.employee-name {
  font-size: 1.1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.employee-details {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  font-size: 0.85rem;
  color: #666;
}

.employee-dept, .employee-position, .employee-phone {
  display: flex;
  align-items: center;
}

/* 员工状态 */
.employee-status {
  padding: 5px 15px;
  border-radius: 20px;
  font-size: 0.8rem;
  font-weight: bold;
  text-align: center;
  min-width: 60px;
}

.employee-status.active {
  background: #d1fae5;
  color: #065f46;
}

.employee-status.inactive {
  background: #fee2e2;
  color: #991b1b;
}
```

## 预期效果
- 薪资结构分析部分被移动到核心人力指标下面
- 该部分显示所有员工的信息，包括姓名、部门、职位、电话和状态
- 员工列表会自动缓慢向上滚动
- 当鼠标悬停在员工项上时，会有明显的悬停效果
- 滚动速度适中，方便查看员工信息

## 技术要点
- 使用CSS实现滚动容器和隐藏滚动条
- 使用JavaScript定时器实现自动滚动
- 实现滚动到顶部时重新开始的循环滚动效果
- 组件销毁时清除定时器，避免内存泄漏
- 响应式设计，适配不同屏幕尺寸

## 注意事项
- 确保后端提供了获取所有员工信息的API接口
- 优化性能，避免大量员工数据导致页面卡顿
- 确保滚动效果流畅，不影响用户体验
- 保持与现有页面风格的一致性