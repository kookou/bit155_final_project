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
    <title>Edit User Info</title>
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
            <div class="auth-box row text-center"></div>

                


            <div class="col-lg-4 col-md-6 col-sm-7 bg-white">
                <div class="p-2"></div>

                <div class="modal-body">
                    <div class="text-center mt-2 mb-4">
                        <a href="index.html" class="text-success">
                            <span>
                                <img class="mr-2" src="assets/images/logo-icon.png" alt="" height="18">
                                <img src="assets/images/logo-text.png" alt="" height="18">
                            </span>
                        </a>
                    </div>

                    <form class="pl-3 pr-3 mt-4" action="#" method="POST" enctype="multipart/form-data">

                        <!--이미지 가운데만 자르는거 혜정언니한테 물어보기-->
                        <div class="form-group user-img text-center">
                            <span>
                                <img src="assets/images/users/4.jpg" alt="userImagePreview"  id="userImagePreview" class="rounded-circle" width="200" height="200">
                            </span>
                        </div>


                        <div class="form-group col-lg-9" style="margin: 16px auto;">
                            <div class="custom-file">
                                <input type="file" class="custom-file-input" id="userImage" accept="image/*">
                                <label class="custom-file-label form-control" for="userImage" style="overflow: hidden;"></label>
                            </div>
                        </div> 

                        <div class="form-group">
                            <input class="form-control" type="nickName" id="nickName" placeholder="Nickname">
                            <div class="invalid-feedback">
                            </div>
                        </div>

                        <div class="form-group">
                            <input class="form-control" type="email" id="email" placeholder="john@deo.com">
                            <div class="invalid-feedback">
                            </div>                            
                        </div>

                        <div class="form-group">
                            <input class="form-control" type="password" id="password" placeholder="Enter your password">                            
                            <div class="invalid-feedback">
                            </div>
                            <input class="form-control mt-2" type="password" id="passwordCheck" placeholder="Password check">                            
                            <div class="invalid-feedback">
                            </div>
                        </div>
                        
                        <div class="col-lg-12 text-center mt-4">
                            <button type="submit" id="submit" class="btn btn-info mr-2" disabled><i class="fas fa-check"></i>
                                &nbsp;Save&nbsp;</button>
                            <input type="reset" id="reset" class="btn btn-secondary ml-2" value=" reset ">

                        </div>
                        <div class="col-lg-12 text-center mt-5 mb-2">
                            Don't want our service anymore? <a href="#" id="delAccount" class="text-muted">Delete account</a>
                        </div>
                    </form>
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
    <!--파일 선택시 파일 경로 표시하기 아래 3개 모두 필요-->
    <!--Custom JavaScript -->
    <script src="dist/js/feather.min.js"></script>
    <!-- slimscrollbar scrollbar JavaScript -->
    <script src="assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js "></script>
    <script src="dist/js/custom.js "></script>
    <!-- ============================================================== -->
    <!-- This page plugin js -->
    <!-- ============================================================== -->
    <script>
        $(".preloader ").fadeOut();


        //파일 미리보기
        var file = document.querySelector('#userImage');

        file.onchange = function() {
            var fileList = file.files;

            // 읽기
            var reader = new FileReader();
            reader.readAsDataURL(fileList[0]);

            //로드 후 미리보기 적용
            reader.onload = function() {
                document.querySelector('#userImagePreview').src = reader.result;
            };
        };


        

        $(function() {
            // console.log("jquery start");

            var emailRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
            var pwdRegExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
            var nickRegExp = /^[0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$/;

            function regCheck(input, regExp, name) { // 정규표현식 검증
                
                if(name == "비밀번호확인"){

                   if( $('#password').hasClass('is-valid') === true ){

                        if($('#password').val() != input.val()){

                            let invalidMessage = '비밀번호가 일치하지 않습니다 <br>';

                            input.removeClass("is-valid");
                            input.addClass("is-invalid");
                            input.next().append(invalidMessage);

                        }else{
                            input.removeClass("is-invalid");
                            input.addClass("is-valid");
                        }

                   }else{
                    input.removeClass("is-valid");
                    input.addClass("is-invalid");

                    let invalidMessage = '형식에 맞는 비밀번호를 먼저 입력하세요 <br>';
                    input.next().append(invalidMessage);
                   }
                            
                }else{
                    
                    if ( regExp.test(input.val()) == false) {
                        input.removeClass("is-valid");
                        input.addClass("is-invalid");

                        let invalidMessage = name + ' 형식에 맞게 입력하세요 <br>';
                        input.next().append(invalidMessage);

                    }else{
                        input.removeClass("is-invalid");
                        input.addClass("is-valid");
                    }
                  
                }
            }

            //이메일 중복체크 기능 들어가야함






            //input 검증 @@ keyup일 때는 제대로 나오는데 blur 일 때는 css가 이상하다........................왜지 
            // chrome 브라우저의 user agent stylesheet 설정 때문이었다............빡침
            function inputCheck(input, regExp, name) {
                input.on({
                    blur : function() {
                        input.next().empty();

                        if (input.val() == "") {
                            input.addClass("is-invalid");

                            let invalidMessage = name + '을 입력하세요 <br>';
                            input.next().append(invalidMessage);

                        }else{
                            regCheck($(this), regExp, name);
                        }
                    },

                    keyup : function() {
                        input.next().empty();
                        regCheck(input, regExp, name);
                    }
                });
            }


            inputCheck($('#email'), emailRegExp, "이메일");
            inputCheck($('#nickName'), nickRegExp, "별명");
            inputCheck($('#password'), pwdRegExp, "비밀번호"); 
            inputCheck($('#passwordCheck'), pwdRegExp, "비밀번호확인");


            //submit 버튼 활성화
            $('input').on('change keyup', function() {
               let nickOk = $('#nickName').hasClass('is-valid');
               let emailOk = $('#email').hasClass('is-valid');
               let pwdOk = $('#password').hasClass('is-valid');
               let pwdChckOk = $('#passwordCheck').hasClass('is-valid');

               if(nickOk && emailOk && pwdOk && pwdChckOk){
                   console.log("if");
                   $('#submit').removeAttr('disabled');
               }else{
                   if(!($('#submit').is(['disabled']))){
                        $('#submit').attr('disabled', 'disabled');
                   }
               }
            });

            // 리셋
            $('#reset').on('click', function() {
                $('input').removeClass('is-invalid');
                $('input').removeClass('is-valid');
            });

            console.log($('delAccount'));
            $('#delAccount').on('click', function() {
                console.log("여기");
               let result = confirm("탈퇴하시겠습니까?"); 

               if(result){
                   console.log("탈퇴진행");                 
               }
            });
        });

        

    </script>
</body>

</html>