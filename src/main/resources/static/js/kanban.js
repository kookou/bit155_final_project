// document.addEventListener('DOMContentLoaded', function () {
//     console.log("되나???????")
//     var docElem = document.documentElement;
//     var kanban = document.querySelector('.kanban-canvas');
//     var board = kanban.querySelector('.kanban-list-wrapper');
//     var itemContainers = Array.prototype.slice.call(kanban.querySelectorAll('.kanban-card-list'));
//     var columnGrids = [];
//     var dragCounter = 0;
//     var boardGrid;
  
//     itemContainers.forEach(function (container) {
  
//       var muuri = new Muuri(container, {
//         items: '.kanban-card-form',
//         layoutDuration: 400,
//         layoutEasing: 'ease',
//         dragEnabled: true,
//         dragSort: function () {
//           return columnGrids;
//         },
//         dragSortInterval: 0,
//         dragContainer: document.body,
//         dragReleaseDuration: 400,
//         dragReleaseEasing: 'ease'
//       })
//       .on('dragStart', function (item) {
//         ++dragCounter;
//         docElem.classList.add('dragging');
//         item.getElement().style.width = item.getWidth() + 'px';
//         item.getElement().style.height = item.getHeight() + 'px';
//       })
//       .on('dragEnd', function (item) {
//         if (--dragCounter < 1) {
//           docElem.classList.remove('dragging');
//         }
//       })
//       .on('dragReleaseEnd', function (item) {
//         item.getElement().style.width = '';
//         item.getElement().style.height = '';
//         columnGrids.forEach(function (muuri) {
//           muuri.refreshItems();
//         });
//       })
//       .on('layoutStart', function () {
//         boardGrid.refreshItems().layout();
//       });
  
//       columnGrids.push(muuri);
  
//     });
  
//     boardGrid = new Muuri(board, {
//       layoutDuration: 400,
//       layoutEasing: 'ease',
//       dragEnabled: true,
//       dragSortInterval: 0,
//       dragStartPredicate: {
//         handle: '.kanban-list-header'
//       },
//       dragReleaseDuration: 400,
//       dragReleaseEasing: 'ease'
//     });
  
// });
//var id = currUser;
var addcardbtn = 
"<div class='kanban-card-add-list' id='addcardel'>"
+ "<a class='kanban-card-add-el btn-light-my' id='addcard' onclick='newtask(this)'>"
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
    
    new1.append("<div class='kanban-list-header'id='listheader'>"+ 
    "<h4 class='kanban-list-title'>"+listName+"</h4>"+"</div>"+ "<a class='kanban-list-menu far fa-trash-alt' data-toggle='modal' data-target='#info-alert-modal'>"
    + "</a>");
    

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
    				//일단 이렇게 해뒀는데 나중에  listNo로 지우는걸로 바꿔야겠다.
    				//왜 return타입이 html이지 ?
    				},
            
    		success: function() {
    			console.log("delete kanbanList");
    		}
    	});
    	
        console.log("delete kanbanList");
        deleteel.remove()
        
//        $.ajax({
//			url: "DeleteKanbanList.ajax",
//			data: {listTitle : listName,
//					id : currUser},
//	        dataType: "html",
//	        
//			success: function(resData) {
//				console.log("list update 완료");
//				// deleteListBtnTag.attr('data-code', resData);
//				// ListDivTag.attr('data-code', resData);
//			}
//		});
        
     });  
	
});



    //카드 추가
$(document).on('click', "#addcard",function(){

	   let kanbanListNo = $(this).parents('.kanban-list-content').attr('data-listno');
	   
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
                   
       
        $(this).parent().before(addcardTag);
        $(this).parent().mouseleave()
        $(this).parent().prev().children().children().children().eq(0).focus()
        var input = $(this).parent().prev().children().children().children().eq(0)
        var comment= $(this).parent().prev().children().children().children().eq(1)
        var file= $(this).parent().prev().children().children().children().eq(2)
        $('.kanban-card-element').removeAttr('data-toggle' ,'modal')
        $('.kanban-card-element').removeAttr( 'data-target', '#card-content')
        comment.hide()
        file.hide()
        
        var kanbanCardNo = $(this).parents().prev()
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

$('#kanban').on('mouseenter','.kanban-card-list',function(){
   
    var mouseeven = $(this).children().eq(0)
    $(this).children().eq(0).show()
    
    $('#kanban').on('mouseleave','.kanban-card-list',function(){
      
        $(mouseeven).hide()

    })

})





//card 수정 
$('#kanban').on('click','.active-card-icon',function(){
    $('.kanban-card-element').removeAttr('data-toggle' ,'modal')
    $('.kanban-card-element').removeAttr( 'data-target', '#card-content')
    
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

    $('textarea').on('focusin',function(){
      
        $('.active-card-icon').hide()
    })
   
    
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

let cardNo =""
var cardtitletext=""
var listtitle=""
var cardtitle=""
var newCardTitleList =""
//모달
$('#kanban').on('click', '.kanban-card-element', function() {
	
//	 $('#modallDescrioptiontextarea').hide();
//	 $('#modallDescrioption').hide();
	 
	 console.log("아냐아아아 ")

     listtitle = $(this).parent().parent().children().eq(0).children().text()
     cardtitletext = $(this).children().eq(0).text()
     cardtitle = $(this).children().eq(0)
     cardNo = $(this).parent().attr("data-cardno")
     
     $('#modaltitle').text(cardtitletext)
     $('.card-in-list').text("in list "+listtitle)
     console.log(cardNo)
     //카드내용
     var title =""
     var content =""
     var writeDate = ""
     var fileCount=""
     var commentCount=""
     var cardIndex=""
     var kanbanListNo=""
     
     $.ajax({ 
			url: "CardContentSelect.ajax",
			data: {
					cardNo: cardNo
					},
	        dataType: "json",
	        
			success: function(resData) {
				console.log("card select 완료");
				console.log(resData)
				title = resData.title 
				content = resData.content
				writeDate = resData.writeDate
				fileCount = resData.fileCount
				commentCount = resData.commentCount
				cardIndex = resData.cardIndex
				kanbanListNo = resData.kanbanListNo
				
				 if(content == "" || content == null){
					 console.log("없다")
					 $('#modallDescrioption').hide();
					 $('#modallDescrioptiontextarea').show();
				 }else{
					 console.log("있다")
					 $('#modallDescrioptiontextarea').hide();
					 $('#modallDescrioption').text(content)
					 $('#modallDescrioption').show();
				 }
				
			} 
		});
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







//댓글 영역
$('#signup-modal').on('click', '.modal-textarea-reply', function(){
    $('.reply-done').show()
})

$('#signup-modal').on('click', '.reply-done', function(){
    if($('#modalreply').val() == "") {
        alert('내용을 입력해 주세요');
        $('#modalreply').focus();
        return;
    }
    var replycontent = $('#modalreply').val()
    console.log(replycontent)
    $('.card-modal-reply-top').after("<div class='card-modal-reply'>" 
    + "<div class='rounded-circle card-modal-profile'"
    + "style='float:left; background-color: white; overflow: hidden; height:35px; width:35px;'>"
    + "<div style='top: 0; left: 0; right: 0; bottom: 0; transform: translate(50%, 50%);'>"
        + "<img src='assets/images/users/ssss.jpg' alt='user' href='javascript:void(0)'"
                + "style='width :auto; height: 50px; transform: translate(-50%, -50%);'>"
        + "</div>"
    + "</div>"
    + "<div class='card-modal-reply-userinfo'>"
    + "<span class='card-modal-reply-username'>sun kim</span>"
        + "<span class='card-modal-reply-date'>2020.06.27 PM 8:38</span>"
    + "</div>"
    + "<div class='card-modal-reply-document display'>"
        + "<div class='card-modal-reply-comment'>"
        + "<p class='card-modal-reply-el'>"+replycontent+"</p>"
        + "<textarea rows='1' class='autosize reply-textarea-edit' id='replyedit' style='overflow: hidden; overflow-wrap: break-word; resize:none; display : none;'>"+replycontent+"</textarea>"
        + "</div>"
    + "</div>"
    + "<div class='modal-reply-btn'>"
            + "<a class='card-modal-reply-delete'"
            + "data-container='body' title='왜안되냐ㅑㅑ' data-toggle='popover' data-placement='bottom'"
            + "data-content='아아'>"
            + "<i class='far fa-trash-alt'></i>Delete</a>"
            + "<a class='card-modal-reply-edit'><i class='far fa-edit'></i>Edit</a>"
        + "</div>"
    + "</div>")
    $('#modalreply').val("")


})

$('#signup-modal').on('click', '.card-modal-reply-delete',function(){
    console.log("올거니?")
})

//모달 리플 수정
$('#signup-modal').on('click', '.card-modal-reply-edit',function(){
    
    
    $(this).parent().prev().children().children().eq(0).hide()
    $(this).parent().prev().children().children().eq(1).show()
    var reolycontent =  $(this).parent().prev().children().children().eq(0)
    var replyinput = $(this).parent().prev().children().children().eq(1)

    // $(replyinput).focus()
    
    var display= $(this).parent().prev()

    $(display).removeClass('display')

    // resize($(replyinput))

    $(replyinput).blur(function() {

    if($(replyinput).val() == "") {
        alert('내용을 입력해 주세요');
        $(replyinput).focus();
        return;
    }

    console.log($(this).parent().prev().children().children().eq(0))

    reolycontent.show()
    replyinput.hide()

    $(reolycontent).text($(replyinput).val())
        console.log(display)
    $(display).addClass('display')

    })
    
	// 	$(replyhide).show();
		
    //     $(replyhide).text($('#replyedit').val());
       
        
	// 	$('#replyedit').remove();
		
    // });
})

$('#modalreply').focusout(function() {
    if($('#modalreply').val() == ""){
        $('.reply-done').hide()
    }
});




//모달 내용 텍스트박스
// $(document).on('click', '.kanban-card-element' , function(){
//     console.log($('.card-modal-list-description').text())
//     var cardmodaldescription = "<textarea rows='1' class='autosize modal-textarea-edit' id='modallisttitle' style='overflow: hidden; overflow-wrap: break-word; resize: none;'></textarea>"
//     if($('.card-modal-list-description').text() == ""){
//         $('.card-modal-description');
//     }

// })




    