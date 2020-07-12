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
    $(this).prev().children().children().children().eq(1).hide();
    $(this).hide()
    console.log($(this).prev().children().children().children().children())
	$(this).prev().children().children().children().children().focus();
});

//리스트 추가 완료
$('#kanban').on('click', '.kanban-addlistdone', function() {
	

    let listName = $(this).parent().children().find('textarea').val();
	console.log(listName)
    let allBoardListNo = Number($('#allBoardListNo').val());
    let kanbanListContent = $(this).parent().parent();
    

	if(listName == "") {
        alert('list title을 입력하세요.');
		$(this).parent().children().find('input').focus();
		return;
    }
    var new1 = $(this).parent().parent().children();
    var addlist = $(this).parent().parent().parent().next()

    $(this).parent().parent().children().children().remove()
    
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
				"listTitle": listName,
				"allBoardListNo": allBoardListNo
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
	let cardTitle = $(this);
	let listName = $(this).text();
	$(this).next().hide();
	$(this).before("<textarea rows='1' class='autosize list-composer-textarea-edit' id='listNameInput' style='overflow: hidden; overflow-wrap: break-word; resize: none;'>"+listName+"</textarea>");
    $('#listNameInput').focus();
    
    $(this).parent().next().hide()
	$(this).hide();
	
	$('#listNameInput').blur(function() {
		if($(this).val() == "") {
			alert('list Name을 입력하세요');
			$(this).focus();
			return;
		}
        var tr = $(this).parent().next()
        
		$('.deleteListDiv').show();
		cardTitle.show();
        cardTitle.text($(this).val());
        console.log($(this))
		$(this).remove();
        tr.show()
        
        $.ajax({
			url: "UpdateKanbanList.ajax",
			data: {listTitle: listName,
					id: currUser},
	        dataType: "html",
	        
			success: function(resData) {
				console.log("list update 완료");
				// deleteListBtnTag.attr('data-code', resData);
				// ListDivTag.attr('data-code', resData);
			}
		});
    });
	
	 
    
});




//리스트 삭제 하기
$('#kanban').on('click', '.kanban-list-menu', function() {
	console.log("=====");
	
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
//	console.log("시작");
//	console.log($(this).parents('.kanban-list-content').attr('data-listno'));
//	
	
        var addcardTag = "<div class='kanban-card-list btn-card-hover' th:each='card : ${kanbancardlist}' th:if='${kanbanlist.kanbanListNo == card.kanbanListNo}'>"
                         +"<span class='icon-pencil active-card-icon' style='position: relative;'></span>"
                         +"<div class='kanban-card-element' data-toggle='modal' data-target='#signup-modal'>"
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
        $('.kanban-card-element').removeAttr( 'data-target', '#signup-modal')
        comment.hide()
        file.hide()

        $(input).blur(function() {
            if($(input).val() == "") {
                alert('Card title을 입력하세요');
                $(input).focus();
                return;
            }
            $(this).parent().text($(input).val());
            $(input).remove();
            $('.kanban-card-element').attr('data-toggle' ,'modal')
            $('.kanban-card-element').attr( 'data-target', '#signup-modal')
            
        });
});

$('#kanban').on('mouseenter','.kanban-card-list',function(){
    console.log($(this).children().eq(0))
    var mouseeven = $(this).children().eq(0)
    $(this).children().eq(0).show()
    
    $('#kanban').on('mouseleave','.kanban-card-list',function(){
        console.log("안되나???")
        $(mouseeven).hide()

    })

})






$('#kanban').on('click','.active-card-icon',function(){
    $('.kanban-card-element').removeAttr('data-toggle' ,'modal')
    $('.kanban-card-element').removeAttr( 'data-target', '#signup-modal')
    
    $(this).mouseleave()
    console.log($(this).parent().children().eq(1).children().eq(0).text())
    var cardelement = $(this).parent().children().eq(1).children().eq(0)
    var cardtext =  $(cardelement).text()
    
    $(cardelement).hide()
    $(cardelement).before("<span class='kanban-card-title'>"+"<textarea rows='1' class='autosize list-card-composer-textarea' placeholder='Enter a title for this card' style='overflow: hidden; overflow-wrap: break-word; resize: none;'>"+cardtext+"</textarea>"+"</span>")
    console.log($(this).parent().children().eq(1).children().find('textarea'))
    $(cardelement).remove();
    var textarea = $(this).parent().children().eq(1).children().find('textarea')

    $(textarea).focus()

    $('textarea').on('focusin',function(){
        console.log("외않되????????????????")
        $('.active-card-icon').hide()
    })
   
    
    $(textarea).blur(function() {
        if($(textarea).val() == "") {
            alert('Card title을 입력하세요');
            $(textarea).focus();
            return;   
        }
        $(this).parent().text($(textarea).val());
        $('.kanban-card-element').attr('data-toggle' ,'modal')
        $('.kanban-card-element').attr( 'data-target', '#signup-modal')


    })

    
})

//모달 타이틀 수정
// $('#kanban').on('click', '.kanban-card-element', function() {
//     console.log($(this).parent().children().eq(0).find('h4').text())
//     var listtitle = $(this).parent().children().eq(0).find('h4').text()
//     var cardtitle = $(this).children().find('span').text()
//     $('#modaltitle').text(cardtitle)
//     $('.card-in-list').text("in list "+listtitle)

$('#signup-modal').on('click','.card-modal-title',function() {
  
    // let cardTitle = $('.kanban-card-title').text();
    // console.log($('.kanban-card-title').text())

	
    $('.card-modal-title').hide();
    
	$('.card-modal-list-name').before("<textarea rows='1' class='autosize modal-textarea-title-edit' id='modallisttitle' style='overflow: hidden; overflow-wrap: break-word; resize: none;'>"+cardtitle+"</textarea>");
    $('#modallisttitle').focus();
    
    $('#modallisttitle').blur(function() {
		if($('#modallisttitle').val() == "") {
			alert('list Name을 입력하세요');
			$('.card-modal-title').focus();
			return;
        }
        
        var tr = $(this).parent().next()
        
		$('.card-modal-title').show();
		
        $('.card-modal-title').text($('#modallisttitle').val());
       
        
		$('#modallisttitle').remove();
		
    });
    
});
// });

//모달 내용 수정
$('#signup-modal').on('click', '.card-modal-list-description', function() {
    
    $('.card-modal-list-description').hide();
    var carddescription = $(this).text();
    console.log(carddescription)
    $('.card-modal-description').after("<textarea rows='1' class='autosize modal-textarea-edit' id='modaldescription' style='overflow: hidden; overflow-wrap: break-word; resize: none;'>"+carddescription+"</textarea>");
    $('#modaldescription').focus();

    $('#modaldescription').blur(function() {
		
        console.log($(this).parent().next())
        var tr = $(this).parent().next()
        
		$('.card-modal-list-description').show();
		
        $('.card-modal-list-description').text($('#modaldescription').val());
       
        console.log($(this))
		$('#modaldescription').remove();
		
    });

});









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




    