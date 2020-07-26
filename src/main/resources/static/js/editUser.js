
    
    
        $(".preloader ").fadeOut();
        //파일 미리보기
        var file = document.querySelector('#userImage');
        file.onchange = function() {
        	console.log("=file.onchange=")
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
        	
             console.log("jquery start");
             
            var pwdRegExp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
            var nickRegExp = /^[0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]{1,10}$/;
            
            // 정규표현식 검증
            function regCheck(input, regExp, name) { 
                
                if(name == "비밀번호확인"){
                   if( $('#pwd').hasClass('is-valid') === true ){
                        if($('#pwd').val() != input.val()){
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
                        
                        if(name == "현재 비밀번호" || name == "새로운 비밀번호"){
                        	invalidMessage += '영문 소문자, 대문자 , 숫자를 1자 이상 포함하여 8자 이상 입력해주세요 <br>';
                        }
                        
                        input.next().append(invalidMessage);
                    }else{
                        input.removeClass("is-invalid");
                        input.addClass("is-valid");
                    }
                  
                }
            }
 
            
            //input 검증
            function inputCheck(input, regExp, name) {
                input.on({
                    blur : function() {
                        input.next().empty();
                        if (input.val() == "") {
                            input.addClass("is-invalid");
                            let invalidMessage = name + '을(를) 입력하세요 <br>';
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

            inputCheck($('#nickName'), nickRegExp, "별명");
            inputCheck($('#pwd'), pwdRegExp, "현재 비밀번호"); 
            inputCheck($('#newPwd'), pwdRegExp, "새로운 비밀번호");
            inputCheck($('#newPwdCheck'), pwdRegExp, "비밀번호확인");
            
            //별명이 형식에 맞지 않을 시 submit 버튼 비활성화
            $('input').on('change keyup', function() {
               let nickNotOk = $('#nickName').hasClass('is-invalid');
               
               console.log("===");
               console.log(nickNotOk);
               
               if(!nickNotOk){
                   console.log("닉네임이 형식에 맞거나 수정안함");
                   $('#submit').removeAttr('disabled');
               }else{
            	   console.log("닉네임이 형식에 맞지 않음");
                   if(!($('#submit').is(['disabled']))){
                        $('#submit').attr('disabled', 'disabled');
                   }
               }
             });
            
            
            
            // 수정 폼 리셋(ie에서는 작동하지 않을 수 있음)
            $('#reset').on('click', function() {
                $('input').removeClass('is-invalid');
                $('input').removeClass('is-valid');
                
            	$("#userImage").val("");

            	$('#userImagePreview').attr('src', (currUserImage.startsWith('https'))
						? currUserImage : 'assets/images/userImage/' + currUserImage);
            	
            	$('#userImageLabel').text(((currUserImage.startsWith('https')
            							  || (currUserImage == 'user.png')))
						? '' : currUserImage);
            });
            
            
            //모달 닫히면 모달 창 reset
            $('#editPassword').on('hide.bs.modal', function(){	
            	$('#editPassword input').val("");  
            	$('#editPassword input').removeClass('is-invalid');
            	$('#editPassword .invalid-feedback').empty();
            	
            });
            
            
            //회원 탈퇴
            $('#delAccount').on('click', function() {
                console.log("여기");
                $(this).removeAttr("href"); // 여기서 href 속성을 없애줘야 controller에서 redirect가 제대로 먹는다
               let result = confirm("정말 탈퇴하시겠습니까? \n지금까지의 모든 활동 기록이 사라집니다."); 
               if(result){
                   console.log("탈퇴진행"); 
                   location.replace('deleteuser?id='+$('#id').val());
               }else{
            	   $(this).attr("href", "");
               } 
            });
            
            
            
            
            
            
        });
        