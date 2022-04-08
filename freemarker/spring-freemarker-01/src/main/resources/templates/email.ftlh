<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Spring Boot Email using FreeMarker</title>
</head>
<body>
<div style="margin-top: 10px">Greetings, ${user.name}</div>
<div>Your username is <b>${user.name}</b></div>
<br/>
<div> Have a nice day..!</div>
<div>
    <table border="1" style=" border-collapse:collapse; border:solid 1px Black;text-indent: 5px;width: 40%">
        <tr>
            <td>日期</td>
            <td>订单总数</td>
            <td>命中数</td>
        </tr>
        <#list records as record>
            <tr>
                <td>${record.date}</td>
                <td>${record.date}</td>
                <td>${record.date}</td>
            </tr>
        </#list>
        <tr>
            <td>20220308</td>
            <td>81</td>
            <td>54</td>
        </tr>
    </table>
</div>
</body>
</html>