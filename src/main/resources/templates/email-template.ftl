<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Sending Email with Freemarker HTML Template Example</title>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>

    <!-- use the font -->
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            font-size: 48px;
        }
    </style>
</head>
<body style="margin: 0; padding: 0;">

    <table align="center" border="0" cellpadding="0" cellspacing="0" width="600" style="border-collapse: collapse;">
        <tr>
            <td align="center" bgcolor="#5f76e8" style="padding: 40px 0 30px 0;">
            </td>
        </tr>
        <tr>
            <td bgcolor="#f5f7ff" style="padding: 40px 30px 40px 30px;">
                <p>${id} 님</p>
                <p>요청하신 인증번호는 ${confirmation} 입니다</p>
                <p>감사합니다</p>
            </td>
        </tr>
        <tr>
            <td bgcolor="#b0b0b0" style="padding: 30px 30px 30px 30px;">
                <p></p>
                <p></p>
            </td>
        </tr>
    </table>

</body>
</html>