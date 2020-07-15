

var addcardbtn = 
"<div class='kanban-card-add-list' id='addcardel'>"
+ "<a class='kanban-card-add-el btn-light-my' id='addcard'>"
   +  "<span class='icon'>+</span>"
    + "<span class='kanban-card-add-text'>Add Card</span>"
 + "</a>"
+ "</div>"

var addlistTag = 
  "<div class='kanban-list-wrapper'>"
    + "<div class='kanban-list-content'>"
        + "<div class='kanban-card' ondrop='drop(event, this)' ondragover='allowDrop(event)'> "
            + "<div class='kanban-list-header-input'id='listheader'>"
            + "<textarea rows='1' class='autosize list-composer-textarea' placeholder='Enter a title for this list' style='overflow: hidden; overflow-wrap: break-word; resize: none; '></textarea>"
            + "</div>"
            + "<a class='kanban-list-menu far fa-trash-alt' data-toggle='modal' data-target='#info-alert-modal'>"
                + "<span class=''></span>"
            + "</a>"
            + "<a class='btn btn-primary kanban-addlistdone'>Add List</a>"
            + "<a class='btn btn-secondary kanban-addlistCancle'>cancle</a>"
        + "</div>"
    + "</div>"
+ "</div>";

function addUploadFileTag(parent, fileName){
	
	let uploadFileTag =  "<div class='card-modal-list-cloudfile'>"
						   +"<p class='card-modal-list-cloud'>"
							   +"<a class=''>"
								   +"<span class='card-modal-filename'>"
								   + fileName
								   +"</span>"
							   +"</a>"
							   +"<span class='card-modal-file-delete far fa-trash-alt'></span>"
						   +"</p>"
					   +"</div>";	
	
	parent.append(uploadFileTag);
}


	
	

	 
	
	




	
var boardName =""

//텍스트에어리어 사이즈 자동 조절
function resize(obj) {
        obj.style.height = "1px";
        obj.style.height = (obj.scrollHeight)+"px";
    }
    $(document).on('keydown','.autosize',function() {
        resize(this);
    });
    $(document).on('focus','.autosize',function() {
        resize(this);
    });
    $(document).on('keyup','.autosize',function() {
        resize(this);
});

$('.redirectBoard').on('click',function(){
	var boardname = $(this).find('.board-name').text()
	boardName = boardname
	console.log($(this).find('.board-name').text())
    
})

$(document).ready(function(){
	console.log("될거니???????")
	console.log(boardName)
	$('#board-name').text(boardName)
})
//리스트 추가
$(document).on('click', '#addlist', function() {

	console.log("리스트 추가 click");
    $(this).before(addlistTag);
    
	let trashIcon = $(this).prev().find('a.fa-trash-alt');
	let titleInputBox = $(this).prev().find('.list-composer-textarea');
      
//    $(this).prev().children().children().children().eq(1).hide(); // 휴지통 아이콘 숨기기    
    trashIcon.hide();  
    $(this).hide()
    
//	$(this).prev().children().children().children().children().focus(); // input on focus
    titleInputBox.focus();
    
});


//리스트 추가 취소
$(document).on('click', '.kanban-addlistCancle', function(){
	let listWrapper = $(this).parents('.kanban-list-wrapper');
	
	listWrapper.remove(); // 인풋 div 제거
	$('#addlist').show(); // +Add List 살리기
	
});




//리스트 추가 완료
$('#kanban').on('click', '.kanban-addlistdone', function() {
	
    let listName = $(this).parent().find('textarea').val();
//	console.log(listName);
		
    let allBoardListNo = Number($('#allBoardListNo').val());
    let kanbanListContent = $(this).parent().parent();
    let titleInputBox = $(this).siblings('div').find('textarea');
    let new1 = $(this).parent();
    //    var new1 = $(this).parent().parent().children();
    let addlist = $(this).parents('.kanban-list-wrapper').next();
    //    var addlist = $(this).parent().parent().parent().next()
    let txtIpTrIconAddBtn = $(this).parent().children();

	if(listName == "") {
        alert('list title을 입력하세요.');        
        titleInputBox.focus(); //input on focus
//		$(this).parent().children().find('input').focus();        
		return;
    }

	txtIpTrIconAddBtn.remove();
//  $(this).parent().parent().children().children().remove()
    
    new1.append("<div class='kanban-list-header'id='listheader'>"
	    		+"<h4 class='kanban-list-title'>"+listName+"</h4>"
	    		+"</div>"
	    		+ "<a class='kanban-list-menu far fa-trash-alt' data-toggle='modal' data-target='#info-alert-modal'>"
	    		+ "</a>"
	    		+ "<div class='divForDragNDrop'></div>");
    

    kanbanListContent.attr('data-title', listName);    
    
    new1.append(addcardbtn);
    var trash = $(this).prev();
    
    $(this).remove();
    addlist.show()

    console.log(listName)
    $.ajax({
		url: "InsertKanbanList.ajax",
		data: {
				"listTitle": $.trim(listName),
				"allBoardListNo": $.trim(allBoardListNo)
				},
        dataType: "text",
		success: function(resData) {
			console.log("list insert 완료");
			kanbanListContent.attr('data-listno', resData);
			console.log(resData)
		}
	});
	
});



//리스트 타이틀 수정
$('#kanban').on('click', '.kanban-list-title', function() {

    let allBoardListNo = Number($('#allBoardListNo').val());
	let kanbanList = $(this);
	let deleteIcon = $(this).parent().next();
	let listName = $(this).text();
	let kanbanListNo = $(this).parents('.kanban-list-content').attr('data-listno');


//	$(this).next().hide();
	$(this).before("<textarea rows='1' class='autosize list-composer-textarea-edit' id='listNameInput' style='overflow: hidden; overflow-wrap: break-word; resize: none;'>"+listName+"</textarea>");
    $('#listNameInput').focus();
    

    deleteIcon.hide(); // 휴지통 아이콘 숨기기
	kanbanList.hide();//여기가 찐 보여지는 제목
	
	$('#listNameInput').blur(function() {
		if($(this).val() == "") {
			alert('list Name을 입력하세요');
			$(this).focus();
			return;
		}
		
		let newKanbanListName = $(this).val(); //변경된 title
        
		$('.deleteListDiv').show();
		
		$(this).remove();
		deleteIcon.show();
        
        $.ajax({
			url: "updateKanbanList.ajax",
			data: {
					"listTitle": $.trim(newKanbanListName),
					"kanbanListNo": $.trim(kanbanListNo)
					},
	        dataType: "json",
	        
			success: function(resData) {
				console.log("list update 완료");
				console.log(resData);
				kanbanList.empty();
				
				kanbanList.text(resData.listTitle);
				kanbanList.show();

			}
		});
    });
	
	 
    
});




//리스트 삭제 하기
$('#kanban').on('click', '.kanban-list-menu', function() {
	
    var listName = $(this).parent().children().eq(0).text()
    let allBoardListNo = Number($('#allBoardListNo').val());
	let kanbanListNo = $(this).parents('.kanban-list-content').attr('data-listno');

	console.log("시작");
	console.log($(this));
    
    
    var canceltext = $('#list-modal-cancel').text()
    var deletetext = $('#list-modal-delete').text()
    var deleteel = $(this).parent().parent().parent()

    $('#list-modal-delete').on("click", function(){ 
    	
    	console.log(listName);
    	
        $.ajax({
    		url: "deleteKanbanList.ajax",
    		data: {
    				"kanbanListNo": $.trim(kanbanListNo)
    				},
            
    		success: function() {
    			console.log("delete kanbanList");
    		}
    	});
    	
        console.log("delete kanbanList");
        deleteel.remove()        
     });  
	
});



    //카드 추가
$(document).on('click', "#addcard",function(){
	   
	   let kanbanListNo = $(this).parents('.kanban-list-content').attr('data-listno');
	   let DnDdiv = $(this).parent().siblings('.divForDragNDrop');

	   
        var addcardTag = "<div class='kanban-card-list btn-card-hover' th:each='card : ${kanbancardlist}' th:if='${kanbanlist.kanbanListNo == card.kanbanListNo}'>"
                         +"<span class='icon-pencil active-card-icon' style='position: relative;'></span>"
                         +"<div class='kanban-card-element' data-toggle='modal' data-target='#card-content'>"
                         +"<span class='kanban-card-title' th:text='${card.cardTitle}' >"
                         +"<textarea rows='1' class='autosize list-card-composer-textarea' placeholder='Enter a title for this card' style='overflow: hidden; overflow-wrap: break-word; resize: none; '></textarea>"
                         +"</span>"
                         +"<div class='kanban-card-badges'>"
                         +'<div class="kanban-card-badge" title="comments" th:if="${card.commentcount >0}">'
                         +'<span class="icon-bubble badge-icon"></span>'
                         +'<span class="badge-text" th:text="${card.commentcount}"></span>'
                         +'</div>'
                         +'<div class="kanban-card-badge" title="file" th:if="${card.fileCount >0}">'
                         +'<span class="icon-paper-clip badge-icon"></span>'
                         +'<span class="badge-text" th:text="${card.fileCount}></span>'
                         +'</div>'
                         +"</div>"
                         +"</div>"
                         +"</div>"
                   
       
	    DnDdiv.append(addcardTag);                         
//        $(this).parent().before(addcardTag);
        
 	   let input = $(this).parent().prev().find('textarea');// 여기는 더해지고 나서 설정돼야함
//     var input = $(this).parent().prev().children().children().children().eq(0)
 	   let comment = $(this).parent().prev().find('[title="comments"]').last();
//     var comment= $(this).parent().prev().children().children().children().eq(1)
 	   let file = $(this).parent().prev().find('[title="file"]').last();
//       var file= $(this).parent().prev().children().children().children().eq(2)
 	   let kanbanCardNo = $(this).parent().siblings('.divForDragNDrop').children().last();
// 	   var kanbanCardNo = $(this).parents().prev()

	   
        $(this).parent().mouseleave() // a#addcardel
//        $(this).parent().prev().children().children().children().eq(0).focus() // addCardInput
        
        input.focus();

        $('.kanban-card-element').removeAttr('data-toggle' ,'modal')
        $('.kanban-card-element').removeAttr( 'data-target', '#card-content')
        
        comment.hide()
        file.hide()
        
        console.log($(this).parent().siblings('.divForDragNDrop').children().last());
        console.log("시작");
	    console.log(kanbanCardNo);
	    
        $(input).blur(function() {
            if($(input).val() == "") {
                alert('Card title을 입력하세요');
                $(input).focus();
                return;
            }
            var cardtitle = $(input).val();
            $(this).parent().text($(input).val());
            console.log("card")
            console.log(cardtitle)
            $(input).remove();
            $('.kanban-card-element').attr('data-toggle' ,'modal')
            $('.kanban-card-element').attr( 'data-target', '#card-content')
            
            $.ajax({
    			url: "InsertKanbanCard.ajax",
    			data: { title: cardtitle,
    					kanbanListNo: kanbanListNo,
    					},
    	        dataType: "html",
    	        
    			success: function(resData) {
    				console.log("card insert 완료");
    				 kanbanCardNo.attr('data-cardno', resData);
    				 console.log(resData);
    			}
    		}); 
        });   
});


//카드 수정 버튼 호버 
$('#kanban').on('mouseenter','.kanban-card-list',function(){
    var mouseeven = $(this).children().eq(0)
    $(this).children().eq(0).show()
    
    $('#kanban').on('mouseleave','.kanban-card-list',function(){
        $(mouseeven).hide()

    })

})

//  $('.active-card-icon').on('mouseenter',function(){
//     removeClass('.btn-card-hovr')
//     removeClass('.active-card-icon');
//     $('.kanban-card-list').off();
//    });
//
//$('.active-card-icon').on('mousedown',function(){
//    removeClass('.btn-card-hovr')
//    removeClass('.active-card-icon');
//    $('.kanban-card-list').off();
//   });
//
//$('.active-card-icon').on('mouseleave',function(){
//    removeClass('.btn-card-hovr')
//    removeClass('.active-card-icon');
//    $('.kanban-card-list').off();
//   });



//card 수정 
$('#kanban').on('click','.active-card-icon',function(){
    $('.kanban-card-element').removeAttr('data-toggle' ,'modal')
    $('.kanban-card-element').removeAttr( 'data-target', '#card-content')
    console.log($(this))
    $(this).mouseleave()
    console.log("카드수정")
    let cardNo = $(this).parent().children().eq(1).children().eq(0).parent().parent().attr("data-cardno")
    var cardelement = $(this).parent().children().eq(1).children().eq(0)
    var cardtext =  $(cardelement).text()
    
    $(cardelement).hide()
    $(cardelement).before("<span class='kanban-card-title'>"+"<textarea rows='1' class='autosize list-card-composer-textarea' placeholder='Enter a title for this card' style='overflow: hidden; overflow-wrap: break-word; resize: none;'>"+cardtext+"</textarea>"+"</span>")
    console.log($(this).parent().children().eq(1).children().find('textarea'))
    $(cardelement).remove();
    var textarea = $(this).parent().children().eq(1).children().find('textarea')

    $(textarea).focus()

    
   
    
    $(textarea).blur(function() {
        if($(textarea).val() == "") {
            alert('Card title을 입력하세요');
            $(textarea).focus();
            return;   
        }
        var cardTitle = $(textarea).val();
        $(this).parent().text($(textarea).val());
        $('.kanban-card-element').attr('data-toggle' ,'modal')
        $('.kanban-card-element').attr( 'data-target', '#card-content')
        
        $.ajax({
			url: "UpdateKanbanCard.ajax",
			data: { title: cardTitle,
					cardNo: cardNo,
					},
	        dataType: "html",
	        
			success: function(resData) {
				console.log("card update 완료");
			}
		});
    })

    
})



//var cardNo ="";
//var cardtitletext="";
//var listtitle="";
//var cardtitle="";
//var cardElements = "";
//var newCardTitleList ="";
//
//var cardCommentcount="";
	
//모달
$('#kanban').on('click', '.kanban-card-element', function() {
	cardElements = $(this).parent();
//	 $('#modallDescrioptiontextarea').hide();
//	 $('#modallDescrioption').hide();

     listtitle = $(this).parent().parent().children().eq(0).children().text()
     cardtitletext = $(this).children().eq(0).text()
     cardtitle = $(this).children().eq(0)
     cardNo = $(this).parent().attr("data-cardno")
     

     $('#modaltitle').text(cardtitletext)
     $('.card-in-list').text("in list "+listtitle)


     
    //카드 내용 유무 체크 후 뿌려줌 
     $.ajax({ 
			url: "CardContentSelect.ajax",
			data: {
					cardNo: cardNo
					},
	        dataType: "json",
	        
			success: function(resData) {
				console.log("card select 완료");
				console.log(resData)
				var cardComment = resData.commentCount;
				cardCommentcount = cardComment
				 if(resData.content == "" || resData.content == null){
					 console.log("없다")
					 $('#modallDescrioption').hide();
					 $('#modallDescrioptiontextarea').show();
				 }else{
					 console.log("있다")
					 $('#modallDescrioptiontextarea').hide();
					 $('#modallDescrioption').text(resData.content)
					 $('#modallDescrioption').show();
				 }
			} 
		});
	 

	 $.ajax({ 
			url: "CardReplySelect.ajax",
			data: {
					cardNo: cardNo
					},
	        dataType: "json",
	        
			success: function(resData) {
			
				console.log("reply select 완료");
				 
				$('.reply-list').empty();
				makereply(resData);
			} 
		});
	 
	 
	 

});


//리플달기 
$('#card-content').on('click', '.reply-done', function(){
	console.log("안되겠지?")
	console.log(cardNo)
	 
    if($('#modalreply').val() == "") {
        alert('내용을 입력해 주세요');
        $('#modalreply').focus();
        return;
    }
	
    var replycontent = $('#modalreply').val()
   
    $('#modalreply').val("")
    
    $.ajax({
		url: "CardReplyInsert.ajax",
		data: {
				cardNo: cardNo,
				content :replycontent,
				id : currUser
				},
        dataType: "html",
        
		success: function(resData) {
			console.log("reply insert 완료");
			
			$.ajax({ 
				url: "CardReplySelect.ajax",
				data: {
						cardNo: cardNo
						},
		        dataType: "json",
		        
				success: function(resData) {
					console.log("reply select 완료2");
					 $.ajax({ 
							url: "CardContentSelect.ajax",
							data: {
									cardNo: cardNo
									},
					        dataType: "json",
					        
							success: function(result) {
								console.log("card select 완료2");
								console.log(result)
								var cardComment = result.commentCount;
								cardCommentcount = cardComment;
								console.log(cardCommentcount);
							} 
						});
					$('.reply-list').empty();
					makereply(resData);
				} 
			});
		}
	});
    
    
    
});

//리플 그리는 함수 
function makereply(resData) {
	 console.log("오니????")
		var replyhtml ="";
		$.each(resData, function(index, obj){
			if(cardNo == obj.cardNo){
				console.log(obj)
				replyhtml += "<div class='card-modal-reply'>" 
				    + "<div class='rounded-circle card-modal-profile'"
				    + "style='float:left; background-color: white; overflow: hidden; height:35px; width:35px;'>"
				    + "<div style='top: 0; left: 0; right: 0; bottom: 0; transform: translate(50%, 50%);'>"
				        + "<img src='assets/images/userimage/"+obj.image+"' alt='user' href='javascript:void(0)'"
				                + "style='width :auto; height: 50px; transform: translate(-50%, -50%);'>"
				        + "</div>"
				    + "</div>"		
				    + "<div class='card-modal-reply-userinfo'>"
				    + "<span class='card-modal-reply-username'>"+obj.nickname+"</span>"
				        + "<span class='card-modal-reply-date'>"+obj.writeDate+"</span>"
				    + "</div>"
				    + "<div class='card-modal-reply-document display'>"
				        + "<div class='card-modal-reply-comment' data-cardreplyno='"+obj.commentNo+"'>"
				        + "<p class='card-modal-reply-el'>"+obj.content+"</p>"
				        + "<textarea rows='1' class='autosize reply-textarea-edit' id='replyedit' style='overflow: hidden; overflow-wrap: break-word; resize:none; display : none;'></textarea>"
				        + "</div>"
				    + "</div>"
				    if(obj.id == currUser){
				    	replyhtml +=
				    	  "<div class='modal-reply-btn'>"
			            + 	"<a class='card-modal-reply-delete'"
			            + 	"data-container='body' data-toggle='popover' data-placement='bottom'"
			            + 	"data-content=''>"
			            + 	"<i class='far fa-trash-alt'></i>Delete</a>"
			            + 	"<a class='card-modal-reply-edit'><i class='far fa-edit'></i>Edit</a>"
			            + "</div>"
				    }
				    
				    + "</div>"
			}
		});
		 $('.reply-list').append(replyhtml);
	}

//$( '.card-modal-content' ).animate( {
//	height: 'auto'
//  } );

//모달 카드 삭제
$('.card-modal-close').on('click',function(){
	 console.log("card delete click");
	 console.log(cardNo);
	 console.log(cardElements);
	 
	 $.ajax({
		 	url: "deleteKanbanCard.ajax",
		 	data:{
		 			"cardNo": cardNo
		 			},
		 	success: function(){
		 		console.log("card delete 성공");		 		
		 		}	
	 	});
	 cardElements.remove();
});



//모달 타이틀 수정  
$('#card-content').on('click','.card-modal-title',function() {
	
	console.log("타이틀 클릭 ")
	console.log(cardNo)
    // let cardTitle = $('.kanban-card-title').text();
    // console.log($('.kanban-card-title').text())
	var newCardTitle="";
	
	$('.card-modal-title').hide();
	$('#modallisttitle').show();
	$('#modallisttitle').val(cardtitletext);
    $('#modallisttitle').focus();
    
    $('#modallisttitle').blur(function() {
		if($('#modallisttitle').val() == "") {
			alert('list Name을 입력하세요');
			$('#modallisttitle').focus();
			return;
        }
                              
        
		$('.card-modal-title').show();
		
        $('.card-modal-title').text($('#modallisttitle').val());
        console.log($('#modallisttitle').val())
        newCardTitle = $('#modallisttitle').val()
        
        newCardTitleList = newCardTitle //모달 밖 카드 타이틀 바꾸기 
        cardtitletext = newCardTitle  //현재 모달창 카드 타이틀 바꾸기 
        
		$('#modallisttitle').hide();
        cardtitle.text(newCardTitleList);
		
        $.ajax({
			url: "UpdateKanbanCard.ajax",
			data: { title: newCardTitle,
					cardNo: cardNo,
					},
	        dataType: "html",
	        
			success: function(resData) {
				console.log("card update 완료");

			}
		});
		
    });
    
});

////모달 타이틀 수정 후 모달 닫힐때 화면에 뿌리는 이벤트 
//$('#card-content').on('hide.bs.modal', function () {
//	console.log(cardtitle)
//	console.log(newCardTitleList)
//	cardtitle.text(newCardTitleList)
//	console.log("될거니?")
//	
//})

//모달 카드 내용 인서트 
$('.modal-textarea-description-edit').on('focus',function(){
	 
	 console.log(cardNo)
	 $('#modallDescrioptiontextarea').val("");
	 $('.modal-textarea-description-edit').on('blur',function(){
			 if($('#modallDescrioptiontextarea').val() ==""){
				 console.log("히")
				 return
			 }else{
				 console.log($('#modallDescrioptiontextarea').val())
				 var cardDescription = $('#modallDescrioptiontextarea').val()
				 $('#modallDescrioptiontextarea').hide();
				 $('#modallDescrioption').text(cardDescription)
				 $('#modallDescrioption').show();
				 $('#modallDescrioptiontextarea').val("");
				 
				 $.ajax({
						url: "CardDescrioptionInsert.ajax",
						data: {
								cardNo: cardNo,
								content :cardDescription
								},
				        dataType: "html",
				        
						success: function(resData) {
							console.log("card 내용 업데이트 완료");
							
						}
					});
			 }
	 })
});


//모달 카드 내용 수정 
$('.card-modal-list-description').on('click',function(){
	 console.log($('#modallDescrioption').text())
	 console.log(cardNo)
	  
	 var cardDescription = $('#modallDescrioption').text()
	 console.log(cardDescription)
	
	 $('#modallDescrioptiontextarea').show();
	 $('#modallDescrioptiontextarea').focus();
	 $('#modallDescrioptiontextarea').val(cardDescription);
	 $('#modallDescrioption').hide();
	 
})

//모달 카드 파일 업로드
$('#kanbanFileInputBtn').on('click',function(){
	 console.log("kanbanFileInputBtn 클릭");
	 console.log($('[data-cardno='+cardNo+']').find('[title=file]'));
	 
	 let allBoardListNo = $('#allBoardListNo').val();
	 let fileCount = $('[data-cardno='+cardNo+']').find('[title=file] .badge-text');
	 
	 $('#inputAllBoardListNo').val(allBoardListNo);
	 $('#inputCardNo').val(cardNo);
	 $('#inputTeamNo').val(teamNo);
	 
	 let formData = new FormData($('#kanbanFileInput')[0]);
	 
	 $.ajax({ 
		 		type: "POST", 
		 		enctype: 'multipart/form-data', // 필수 
		 		url: 'kanbanFilesUpload.ajax', 
		 		data: formData,  // 필수
		 		processData: false, // 필수 
		 		contentType: false, // 필수 
		 		cache: false, 
		 		success: function(resData) {
		 			if((resData != null) && (resData.length > 0)){
		 				console.log(resData.length);
		 				console.log("파일 업로드 성공");	
		 				
		 				$.each(resData, function(index, fileName){
		 					addUploadFileTag($('#cardModalFileList'), fileName);
		 				});
		 				
		 				fileCount.text(Number(fileCount.text()) + resData.length);
		 				
		 			}else{
		 				console.log("업로드된 파일이 없습니다");
		 			}
		 			
		 		}, 
		 		error: function (e) { 
		 			console.log("ajax 에러발생");
		 		} 
		 	});

	 
})







//댓글 영역



$('#card-content').on('click', '.card-modal-reply-delete',function(){
    var commentNo = $(this).parent().prev().children().attr('data-cardreplyno')
    console.log(commentNo)
    $.ajax({
		url: "CardReplyDelete.ajax",
		data: {
				commentNo : commentNo
				},
        dataType: "html",
        
		success: function() {
			console.log("reply delete 완료");
			
			$.ajax({ 
				url: "CardReplySelect.ajax",
				data: {
						cardNo: cardNo
						},
		        dataType: "json",
		        
				success: function(resData) {
				
					console.log("reply select 완료2");
					console.log(resData);
					$('.reply-list').empty();
					makereply(resData);
				} 
			});
		}
	});
})

//모달 리플 수정
$('#card-content').on('click', '.card-modal-reply-edit',function(){
	
	var cardCommentcount = $(this).parent().prev().children().attr('data-cardreplyno')
    $(this).parent().prev().children().children().eq(0).hide()
    $(this).parent().prev().children().children().eq(1).show()
    var replycontent =  $(this).parent().prev().children().children().eq(0)
    var replyinput = $(this).parent().prev().children().children().eq(1)
    
    $(this).parent().prev().children().children().eq(1).focus()
    replyinput.text(replycontent.text());
	console.log(replycontent.text())
    var display= $(this).parent().prev()

    $(display).removeClass('display')



    $(replyinput).blur(function() {
    	
    if($(replyinput).val() == "") {
        $(replyinput).focus();
        return;
    }

    console.log(cardCommentcount)
    var newRelycontent = $(replyinput).val()
    
    $(replycontent).val(newRelycontent)
   
    console.log(newRelycontent)
    
    $(display).addClass('display');
    
    $.ajax({
		url: "CardReplyUpdate.ajax",
		data: {
				commentNo : cardCommentcount,
				content : newRelycontent,
				},
				
        dataType: "html",
        
		success: function() {
			console.log("reply Update 완료");
			
			$.ajax({ 
				url: "CardReplySelect.ajax",
				data: {
						cardNo: cardNo
						},
		        dataType: "json",
		        
				success: function(resData) {
				
					console.log("reply select 완료2");
					console.log(resData);
					$('.reply-list').empty();
					makereply(resData);
				} 
			});
		}
	});

    })
    
	
})

//$('#modalreply').focusout(function() {
//    if($('#modalreply').val() == ""){
//        $('.reply-done').hide()
//    }
//});




//모달 내용 텍스트박스
// $(document).on('click', '.kanban-card-element' , function(){
//     console.log($('.card-modal-list-description').text())
//     var cardmodaldescription = "<textarea rows='1' class='autosize modal-textarea-edit' id='modallisttitle' style='overflow: hidden; overflow-wrap: break-word; resize: none;'></textarea>"
//     if($('.card-modal-list-description').text() == ""){
//         $('.card-modal-description');
//     }

// })




    