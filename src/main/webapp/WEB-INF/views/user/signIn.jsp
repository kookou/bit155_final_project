<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>	
<html dir="ltr" lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="assets/images/favicon.png">
    <title>sign in</title>
    <!-- Custom CSS -->
    <link href="dist/css/style.min.css" rel="stylesheet">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body>
    <div class="main-wrapper">
        <!-- ============================================================== -->
        <!-- Preloader - style you can find in spinners.css -->
        <!-- ============================================================== -->
        <div class="preloader">
            <div class="lds-ripple">
                <div class="lds-pos"></div>
                <div class="lds-pos"></div>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- Preloader - style you can find in spinners.css -->
        <!-- ============================================================== -->
        <!-- ============================================================== -->
        <!-- Login box.scss -->
        <!-- ============================================================== -->
        <div class="auth-wrapper d-flex no-block justify-content-center align-items-center position-relative">
            


                <div class="col-lg-4 col-md-6 col-sm-7 bg-white">
                    <div class="p-2">


                        <div class="modal-body">
                            <div class="text-center mt-2 mb-4">
                                <a href="index.html" class="text-success">
                                    <span><img class="mr-2" src="assets/images/logo-icon.png"
                                            alt="" height="18"><img
                                            src="assets/images/logo-text.png" alt=""
                                            height="18"></span>
                                </a>
                            </div>

                            <form action="" class="pl-3 pr-3 mt-4" onsubmit='return submitCheck();' method="post">

                                <div class="form-group">
                                    <input class="form-control" type="text" id="id" name="id" placeholder="john@deo.com">
                                    <div class="invalid-feedback">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <input class="form-control" type="password" id="pwd" name="pwd" placeholder="Enter your password">
                                    <div class="invalid-feedback">
                                    </div>        
                                </div>

                                <div class="form-group">
                                    <div class="custom-control custom-checkbox">
                                        <input type="checkbox" class="custom-control-input"
                                            id="customCheck2">
                                        <label class="custom-control-label"
                                            for="customCheck2">Remember me</label>
                                    </div>
                                </div>

                                <div class="form-group text-center mt-4">
                                    <input type="submit" class="btn btn-block btn-info" value="Sign In" />
                                </div>

                                <div class="col-lg-12 text-center mt-5">
                                    Don't have an account? <a href="signup" class="text-danger">Sign Up</a>
                                </div>
                                <div class="col-lg-12 text-center mt-1 mb-2">
                                    <a href="resetpassword" class="text-danger">Forgot your password?</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- ============================================================== -->
        <!-- Login box.scss -->
        <!-- ============================================================== -->
    </div>
    <!-- ============================================================== -->
    <!-- All Required js -->
    <!-- ============================================================== -->
    <script src="assets/libs/jquery/dist/jquery.min.js "></script>
    <!-- Bootstrap tether Core JavaScript -->
    <script src="assets/libs/popper.js/dist/umd/popper.min.js "></script>
    <script src="assets/libs/bootstrap/dist/js/bootstrap.min.js "></script>
    <!-- ============================================================== -->
    <!-- This page plugin js -->
    <!-- ============================================================== -->
    <script>
        $(".preloader ").fadeOut();

        function submitCheck() { // 바깥에 있어야 실행됨
            console.log("hmm");
            let invalidMessage = "을 입력하세요 <br>";
            $('.invalid-feedback').empty();
            $('input').removeClass('is-invalid');

            if($('#id').val() == ""){
                $('#id').addClass('is-invalid');
                
                invalidMessage = "이메일" + invalidMessage;
                $('#id').next().append(invalidMessage);
                return false;
            }

            if($('#pwd').val() == ""){
                $('#pwd').addClass('is-invalid');

                invalidMessage = "비밀번호" + invalidMessage;
                $('#pwd').next().append(invalidMessage);
                return false;
            }
            return true;
        }

        //로그인 유지 기능 구현해야함

        $(function() {
            // console.log("jquery start");
        });

    </script>
</body>

</html>