<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>	<!-- js 분리 아직 안함 
					 js에 입력받은 이메일이 회원 DB에 없으면 알려주는 내용(ajax)을 추가해야 할 것 같다-->
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
    <title>Reset Password</title>
    <!-- Custom CSS -->
    <link href="dist/css/style.min.css" rel="stylesheet">
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
            


                <div class="col-lg-4 col-md-6 col-sm-7 bg-white"> <!-- sm class 추가  -->
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

                            <div class="text-center mt-2 mb-4">
                                Just need to confirm your email <br>
                                to send you instructions to reset your password.
                            </div>

                            <form action="#" class="pl-3 pr-3 mt-4">

                                <div class="form-group" id="restPwdFormGroup">
                                    <!-- keyup 처리하기 -->
                                    <input class="form-control" type="email" id="email"
                                        required="" placeholder="john@deo.com">

                                    <div class="invalid-feedback">
                                    </div>
                                </div>

                                <div class="form-group text-center mt-4">
                                    <button type="submit" id="submit" class="btn btn-block btn-info" disabled>Reset Password</button>
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

        $(function() {
            // console.log("jquery start");

            var emailRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

            function emailRegCheck(emailInput) { // 이메일 정규표현식 검증
                if ( emailRegExp.test(emailInput.val()) == false) {
                    emailInput.addClass("is-invalid");

                    let invalidMessage = '이메일 형식에 맞게 입력하세요 <br>';

                    $('.invalid-feedback').append(invalidMessage);

                }else{
                    emailInput.removeClass("is-invalid");
                    $('#submit').removeAttr('disabled', 'disabled');
                }
            }

            $('#email').on('blur', function() {
            //  console.log("blur event");
             
             $('.invalid-feedback').empty();
             
             if ($(this).val() == "") {
                 $(this).addClass("is-invalid");

                let invalidMessage = '이메일을 입력하세요 <br>';

                $('.invalid-feedback').append(invalidMessage);

             }else{
                emailRegCheck($(this));
             }
            });


            $('#email').on('keyup', function() {
            //  console.log("keyup event");

             $('.invalid-feedback').empty();
             emailRegCheck($(this));
             
            });

        });
    </script>
</body>
