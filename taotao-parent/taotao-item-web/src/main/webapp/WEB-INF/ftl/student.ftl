<html>
    <head>
        <title>测试页面</title>
    </head>
    <body>
        学生信息：<br>
        学号：${student.id}<br>
        姓名：${student.name}<br>
        年龄：${student.age}<br>
        家庭住址：${student.address}<br>

        <table border="1">
            <tr>
                <th>序号</th>
                <th>学号</th>
                <th>姓名</th>
                <th>年龄</th>
                <th>家庭住址</th>
            </tr>
            <#--循环取值-->
            <#list stuList as stu>
            <#--if判断-->
                <#if stu_index%2 ==0>
                    <tr bgcolor="red"/>
                <#else >
                    <tr bgcolor="blue"/>
                </#if>
                <tr>
                    <#--取循环的下标-->
                    <td>${stu_index}</td>
                    <td>${stu.id}</td>
                    <td>${stu.name}</td>
                    <td>${stu.age}</td>
                    <td>${stu.address}</td>
                </tr>
            </#list>
        </table>
        <br>
        日期类型的处理 ${date?string('yyyy/MM/dd HH:mm:ss')}
        <br>
        null值的处理： ${vall!"默认值"}
        <br>
        使用if判断null
        <#if val??>
            val是有值得
            <#else >
            val为null
        </#if>
        <br>
        使用include包含模板
        <#include "hello.ftl">
    </body>

</html>