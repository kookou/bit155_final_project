

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

var uploadFileTag =  "<div class='card-modal-list-cloudfile'>"
						   +"<p class='card-modal-list-cloud'>"
							   +"<a class='card-modal-fileLink' download>"
								   +"<span class='card-modal-filename'>"
								   +"</span>"
							   +"</a>"
							   +"<span class='card-modal-file-delete far fa-trash-alt'></span>"
						   +"</p>"
					+"</div>";

function addUploadFileTag(parent, file){
	
	let filePath = 'cloud/'+ $('#teamNo').val() + '/' + file.fileName;
	
	parent.append(uploadFileTag);
	parent.find('.card-modal-filename').last().append(file.originFileName);
	parent.find('.card-modal-file-delete').last().attr('fileNo', file.fileNo);
	parent.find('.card-modal-fileLink').last().attr('href', filePath);
	parent.find('.card-modal-fileLink').last().attr('download', file.originFileName);
}

function addCardFileCountTag(parent, fileCount){
	let cardFileCountIcon = "<div class='kanban-card-badge' title='file'>"
								+"<span class='icon-paper-clip badge-icon'></span>"
								+"<span class='badge-text'>"
								+ fileCount
								+"</span>"
							+"</div>";
	parent.append(cardFileCountIcon);
}


var startListIDX = "";
var endListIDX = "";

var currentCardNo = "";
var startListNo = "";
var endListNo = "";
var startCardIDX = "";
var endCardIDX = "";




 $('.divForDragNDrop').sortable({
	 connectWith: '.divForDragNDrop',
	 start( event, ui ){
		 console.log("start");
		 currentCardNo = ui.item.data('cardno');
		 console.log(currentCardNo);
		 startCardIDX = ui.item.index();
		 console.log(startCardIDX);
		 startListNo = ui.item.parents('div.kanban-list-content').data('listno');
		 console.log(startListNo);
		 
	 },
	 receive: function(event, ui){ // 다른 리스트간 이동
		 console.log("receive");
		 endCardIDX = ui.item.index();
       	 console.log(endCardIDX);
       	 endListNo = ui.item.parents('div.kanban-list-content').data('listno');
		 console.log(endListNo);

       	 
       	 if(startListNo != endListNo){
       		 
       		
       		 $.ajax({
       				url: "resortKanbanCard.ajax",
       				data: {
       						"allBoardListNo": $.trim($('#allBoardListNo').val()),
       						"kanbanCardNo": $.trim(currentCardNo),
       						"startListNo": $.trim(startListNo),
       						"endListNo": $.trim(endListNo),
       						"startCardIDX": $.trim(startCardIDX),
       						"endCardIDX": $.trim(endCardIDX) 
       						},
       				success: function() {
		       					console.log("resortKanbanList 완료");
       						},
       				error: function(e){
       					console.log("ajax error");
       				}
       		 });
       	 }
	 },
	 stop( event, ui ){ // 같은 리스트 내에서 이동
     	console.log("stop");
     	console.log(ui.item.parent());
     	
     	//같은 카드 내에서 이동될 경우 recive를 사용할 수 없다
     	endCardIDX = ui.item.index();
     	console.log(endCardIDX);
     	endListNo = ui.item.parents('div.kanban-list-content').data('listno');
     	console.log(endListNo);

     	
      	 if((startListNo == endListNo) && (startCardIDX != endCardIDX)){
        		
       		 $.ajax({
       				url: "resortKanbanCard.ajax",
       				data: {
       						"allBoardListNo": $.trim($('#allBoardListNo').val()),
       						"kanbanCardNo": $.trim(currentCardNo),
       						"startListNo": $.trim(startListNo),
       						"endListNo": $.trim(endListNo),
       						"startCardIDX": $.trim(startCardIDX),
       						"endCardIDX": $.trim(endCardIDX) 
       						},
       				success: function() {
		       					console.log("resortKanbanList 완료");
       						},
       				error: function(e){
       					console.log("ajax error");
       				}
       		 });
       	 }
     	
//    	kanbanListArr = ui.item.parent().find('.kanban-list-content');
//    	
//    	$.each(kanbanListArr, function(index, item){
//    		$(item).attr('data-listindex', index); // 재정렬된 요소에 index 속성 새로 부여하기
//    	});
		 
	 }
	 
 });


 $('#kanban').sortable({ // 상위요소
   	 items: ".kanban-list-wrapper",
        itemOrientation: "horizontal",
        handle: ".kanban-list-title", // 이부분 주석처리하면 버튼도 움직임..
        moveItemOnDrop: true,
        start( event, ui ){
       	 console.log("start");
       	 startListIDX = ui.item.index();
        },
        deactivate( event, ui ){
	       	 console.log("deactivate");
	       	 endListIDX = ui.item.index();
	       	 let currentListNo = ui.item.children().data('listno');
	       	 
	       	 if(!(startListIDX == endListIDX)){
	       		 
	       		
	       		 $.ajax({
	       				url: "resortKanbanList.ajax",
	       				data: {
	       						"allBoardListNo": $.trim($('#allBoardListNo').val()),
	       						"kanbanListNo": $.trim(currentListNo),
	       						"startListIDX": $.trim(startListIDX),
	       						"endListIDX": $.trim(endListIDX)
	       						},
	       				success: function() {
			       					console.log("resortKanbanList 완료");
	       						},
	       				error: function(e){
	       					console.log("ajax error");
	       				}
	       		 });
	       	 }
        },
        stop( event, ui ){
        	console.log("stop");
        	kanbanListArr = ui.item.parent().find('.kanban-list-content');
        	
        	$.each(kanbanListArr, function(index, item){
        		$(item).attr('data-listindex', index); // 재정렬된 요소에 index 속성 새로 부여하기
        	});
        }
        
    });


// $( ".kanban-list-add-wrapper" ).sortable( "disable" );
	

	 
	
	




	
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


    

    $('#kanban').sortable({ // 상위요소
   	 items: ".kanban-list-wrapper",
        itemOrientation: "horizontal",
        handle: ".kanban-list-title", // 이부분 주석처리하면 버튼도 움직임..
        moveItemOnDrop: true,
        
        start( event, ui ){
       	 console.log("start");
       	 startListIDX = ui.item.index();
        },
        deactivate( event, ui ){
	       	 console.log("deactivate");
	       	 endListIDX = ui.item.index();
	       	 let currentListNo = ui.item.children().data('listno');
	       	 
	       	 if(!(startListIDX == endListIDX)){
	       		 
	       		
	       		 $.ajax({
	       				url: "resortKanbanList.ajax",
	       				data: {
	       						"allBoardListNo": $.trim($('#allBoardListNo').val()),
	       						"kanbanListNo": $.trim(currentListNo),
	       						"startListIDX": $.trim(startListIDX),
	       						"endListIDX": $.trim(endListIDX)
	       						},
	       		        dataType: "json",
	       				success: function() {
			       					console.log("resortKanbanList 완료");
	       						},
	       				error: function(e){
	       					console.log("ajax error");
	       				}
	       		 });
	       	 }
        },
        stop( event, ui ){
        	console.log("stop");
        	kanbanListArr = ui.item.parent().find('.kanban-list-content');
        	
        	$.each(kanbanListArr, function(index, item){
        		$(item).attr('data-listindex', index); // 재정렬된 요소에 index 속성 새로 부여하기
        	});
        }
        
    });
    
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
		
    let allBoardListNo = Number($('#allBoardListNo').val());
    let kanbanListContent = $(this).parent().parent();
    let titleInputBox = $(this).siblings('div').find('textarea');
    let new1 = $(this).parent();
    let addlist = $(this).parents('.kanban-list-wrapper').next();
    let txtIpTrIconAddBtn = $(this).parent().children();
    let newKanbanList = $(this).parents().find('.kanban-list-wrapper').last();
    
    //추가된 리스트 index >> 어차피 index는 sortable에서 가져오는데 이게 필요할까?
    let kanbanListIndex = newKanbanList.index();
    

	if(listName == "") {
        alert('list title을 입력하세요.');        
        titleInputBox.focus(); //input on focus       
		return;
    }

	txtIpTrIconAddBtn.remove();
    
    new1.append("<div class='kanban-list-header'id='listheader'>"
	    		+"<h4 class='kanban-list-title'>"+listName+"</h4>"
	    		+"</div>"
	    		+ "<a class='kanban-list-menu far fa-trash-alt' data-toggle='modal' data-target='#info-alert-modal'>"
	    		+ "</a>"
	    		+ "<div class='divForDragNDrop'></div>")
	    		  .find('.divForDragNDrop').sortable({
	    			 connectWith: '.divForDragNDrop'
	    		 });
    

    kanbanListContent.attr('data-title', listName);			//속성에 listName추가하기
	kanbanListContent.attr('data-listindex', kanbanListIndex);    //속성에 index 추가하기
    
    new1.append(addcardbtn);
    var trash = $(this).prev();
    
    $(this).remove();
    addlist.show()

    console.log(listName)
    $.ajax({
		url: "InsertKanbanList.ajax",
		data: {
				"listTitle": $.trim(listName),
				"kanbanListIndex": $.trim(kanbanListIndex),
				"allBoardListNo": $.trim(allBoardListNo)
				},
        dataType: "text",
		success: function(resData) {
			console.log("list insert 완료");
						
			kanbanListContent.attr('data-listno', resData);
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
//	   let cardCount = $(this).parents().prev().children().prevAll('.kanban-card-list')    카드 갯수 

	   let kanbanListNo = $(this).parents('.kanban-list-content').attr('data-listno');
	   let DnDdiv = $(this).parent().siblings('.divForDragNDrop');
	   console.log('cardIndex')
//	   console.log(cardIndex)
	   
        var addcardTag = "<div class='kanban-card-list btn-card-hover'>"
                         +"<span class='icon-pencil active-card-icon' style='position: relative;'></span>"
                         +"<div class='kanban-card-element' data-toggle='modal' data-target='#card-content'>"
                         +"<span class='kanban-card-title' >"
                         +"<textarea rows='1' class='autosize list-card-composer-textarea' placeholder='Enter a title for this card' style='overflow: hidden; overflow-wrap: break-word; resize: none; '></textarea>"
                         +"</span>"
                         +"<div class='kanban-card-badges'>"
                         +'<div  title="comments">'
                         
                         +'</div>'
                         +'<div title="file">'
                        
                         +'</div>'
                         +"</div>"
                         +"</div>"
                         +"</div>"
                   
       
	    DnDdiv.append(addcardTag);                         
//        $(this).parent().before(addcardTag);
        
 	   let input = $(this).parent().prev().find('textarea');// 여기는 더해지고 나서 설정돼야함
 	   let comment = $(this).parent().prev().find('[title="comments"]').last();
 	   let file = $(this).parent().prev().find('[title="file"]').last();
 	   let kanbanCardNo = $(this).parent().siblings('.divForDragNDrop').children().last();

 	 
        $(this).parent().mouseleave() // a#addcardel
        
        input.focus();
        
        let cardIndex = kanbanCardNo.index();
        
        $('.kanban-card-element').removeAttr('data-toggle' ,'modal')
        $('.kanban-card-element').removeAttr( 'data-target', '#card-content')
        
        kanbanCardNo.attr('data-cardindex', cardIndex)
        
        console.log($(this).parent().siblings('.divForDragNDrop').children().last());
        console.log("index는 0부터 시작함");
	    console.log(kanbanCardNo.index());
	    
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
    					cardIndex :cardIndex,
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
        


        $('.divForDragNDrop').sortable({
       	 connectWith: '.divForDragNDrop',
       	 start( event, ui ){
       		 console.log("start");
       		 currentCardNo = ui.item.data('cardno');
       		 console.log(currentCardNo);
       		 startCardIDX = ui.item.index();
       		 console.log(startCardIDX);
       		 startListNo = ui.item.parents('div.kanban-list-content').data('listno');
       		 console.log(startListNo);
       		 
       	 },
       	 receive: function(event, ui){ // 다른 리스트간 이동
       		 console.log("receive");
       		 endCardIDX = ui.item.index();
              	 console.log(endCardIDX);
              	 endListNo = ui.item.parents('div.kanban-list-content').data('listno');
       		 console.log(endListNo);

              	 
              	 if(startListNo != endListNo){
              		 
              		
              		 $.ajax({
              				url: "resortKanbanCard.ajax",
              				data: {
              						"allBoardListNo": $.trim($('#allBoardListNo').val()),
              						"kanbanCardNo": $.trim(currentCardNo),
              						"startListNo": $.trim(startListNo),
              						"endListNo": $.trim(endListNo),
              						"startCardIDX": $.trim(startCardIDX),
              						"endCardIDX": $.trim(endCardIDX) 
              						},
              				success: function() {
       		       					console.log("resortKanbanList 완료");
              						},
              				error: function(e){
              					console.log("ajax error");
              				}
              		 });
              	 }
       	 },
       	 stop( event, ui ){ // 같은 리스트 내에서 이동
            	console.log("stop");
            	console.log(ui.item.parent());
            	
            	//같은 카드 내에서 이동될 경우 recive를 사용할 수 없다
            	endCardIDX = ui.item.index();
            	console.log(endCardIDX);
            	endListNo = ui.item.parents('div.kanban-list-content').data('listno');
            	console.log(endListNo);

            	
             	 if((startListNo == endListNo) && (startCardIDX != endCardIDX)){
               		
              		 $.ajax({
              				url: "resortKanbanCard.ajax",
              				data: {
              						"allBoardListNo": $.trim($('#allBoardListNo').val()),
              						"kanbanCardNo": $.trim(currentCardNo),
              						"startListNo": $.trim(startListNo),
              						"endListNo": $.trim(endListNo),
              						"startCardIDX": $.trim(startCardIDX),
              						"endCardIDX": $.trim(endCardIDX) 
              						},
              				success: function() {
       		       					console.log("resortKanbanList 완료");
              						},
              				error: function(e){
              					console.log("ajax error");
              				}
              		 });
              	 }
            	
//           	kanbanListArr = ui.item.parent().find('.kanban-list-content');
//           	
//           	$.each(kanbanListArr, function(index, item){
//           		$(item).attr('data-listindex', index); // 재정렬된 요소에 index 속성 새로 부여하기
//           	});
       		 
       	 }
       	 
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
var cardCommentcount="";
var cardFilecount="";
//모달
$('#kanban').on('click', '.kanban-card-element', function() {
	 cardElements = $(this).parent();
//	 $('#modallDescrioptiontextarea').hide();
//	 $('#modallDescrioption').hide();

     listtitle = $(this).parent().parent().parent().children().eq(0).text()
     cardtitletext = $(this).children().eq(0).text()
     cardtitle = $(this).children().eq(0)
     cardNo = $(this).parent().attr("data-cardno")
     console.log("여기체크")
     console.log(cardNo)

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
				cardCommentcount = cardComment;
				var cardFile = resData.fileCount;
				cardFilecount = cardFile;
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


	 //모달 실행시 파일 목록 뿌려주기
	 $.ajax({ 
			url: "cardFilesSelect.ajax",
			data: {
					cardNo: cardNo
					},
	        dataType: "json",	        
			success: function(resData) {
			
				console.log("file select 완료");
				console.log(resData);
				
				$('#cardModalFileList').empty();

				$.each(resData, function(index, item){
					addUploadFileTag($('#cardModalFileList'), item);
				});
				
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
								console.log("인서트 코멘트 카운트 셀렉트");
								console.log(result)
								
								var cardComment = result.commentCount;
								cardCommentcount = cardComment;
								

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
	console.log(cardCommentcount)
    
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
        console.log($('#modallisttitle').val());
        newCardTitle = $('#modallisttitle').val();
        
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


// 모달 닫힐때 화면에 뿌리는 이벤트 

$('#card-content').on('hide.bs.modal', function () {
	
	let fileElement = $('[data-cardno='+cardNo+']').find('[title=file] ');
	let commentElement = $('[data-cardno='+cardNo+']').find('[title=comments]');
	
	let fileDiv = '<span class="icon-paper-clip badge-icon"></span> <span class="badge-text">'+cardFilecount+'</span>'
	let commentDiv = '<span class="icon-bubble badge-icon"></span> <span class="badge-text">'+cardCommentcount+'</span>'
	console.log("파일 카운트?")	
	console.log((cardFilecount == 0));
	console.log(cardFilecount);
	console.log("코멘트 카운트?")	
	console.log((cardCommentcount == 0));
	console.log(cardCommentcount);

	if(cardFilecount == 0 && cardCommentcount == 0){
		console.log("파일 0 / 코멘트 0")
		fileElement.empty();
		fileElement.removeClass('kanban-card-badge')
		commentElement.empty();
		commentElement.removeClass('kanban-card-badge')
		return;
	}
	
	if(cardFilecount > 0 && cardCommentcount == 0){
		console.log("파일 n / 코멘트 0")
		fileElement.empty();
		fileElement.append(fileDiv);
		fileElement.addClass('kanban-card-badge');
		commentElement.empty();
		commentElement.removeClass('kanban-card-badge')
		return;
	}
	
	if(cardFilecount == 0 && cardCommentcount > 0){
		console.log("파일 0 / 코멘트 n")
		commentElement.empty();
		commentElement.append(commentDiv);
		commentElement.addClass('kanban-card-badge')
		fileElement.empty();
		fileElement.removeClass('kanban-card-badge')
		return;
	}
	if(cardFilecount > 0 && cardCommentcount > 0){
		console.log("파일 n / 코멘트 n")
		fileElement.empty();
		fileElement.append(fileDiv);
		fileElement.addClass('kanban-card-badge');
		commentElement.empty();
		commentElement.append(commentDiv);
		commentElement.addClass('kanban-card-badge')
		return;
	};
	
});

//모달 카드 내용 인서트 
$('.modal-textarea-description-edit').on('focus',function(){
	 
	 console.log(cardNo);
	 $('#modallDescrioptiontextarea').val("");
	 $('.modal-textarea-description-edit').on('blur',function(){
			 if($('#modallDescrioptiontextarea').val() ==""){
				 console.log("히")
				 return
			 }else{
				 console.log($('#modallDescrioptiontextarea').val())
				 var cardDescription = $('#modallDescrioptiontextarea').val()
				 $('#modallDescrioptiontextarea').hide();
				 $('#modallDescrioption').text(cardDescription);
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
	 
	 let allBoardListNo = $('#allBoardListNo').val();

	 
	 $('#inputAllBoardListNo').val(allBoardListNo);
	 $('#inputCardNo').val(cardNo);
	 console.log(cardNo);
	 $('#inputTeamNo').val(teamNo);
	 
	 let formData = new FormData($('#kanbanFileInput')[0]);
	 
	 $.ajax({ 
		 		type: "POST", 
		 		enctype: 'multipart/form-data', // 필수 
		 		url: 'kanbanFilesUpload.ajax', //여기 작업중
		 		data: formData,  // 필수
		 		processData: false, // 필수 
		 		contentType: false, // 필수 
		 		cache: false, 
		 		success: function(resData) { // 여기 resData 파일 객체 가져오는걸로 바꿔야함
		 			if(resData.length > 0){
		 				console.log(resData.length);
		 				console.log("파일 업로드 성공");	
		 				
		 				$.each(resData, function(index, item){
		 					addUploadFileTag($('#cardModalFileList'), item);
		 				});
		 				
		 				cardFilecount += resData.length;
		 				
//		 				일단 주석처리 Sunn
		 				///////////////// 다시 셀렉트 ////////////////////
//
//		 				 $.ajax({ 
//								url: "CardContentSelect.ajax",
//								data: {
//										cardNo: cardNo
//										},
//						        dataType: "json",
//						        
//								success: function(result) {
//									console.log("인서트 파일/코멘트 카운트 셀렉트");
//									var cardComment = result.commentCount;
//									cardCommentcount = cardComment;
//									
//									var cardFile = result.fileCount;
//									cardFilecount = cardFile;
//								} 
//							});
		 				 		 				 

		 				 // input label 비우기
		 				 $('#kanbanFiles').siblings('.custom-file-label').text("Choose file");
		 				
		 			}else{
		 				console.log("업로드된 파일이 없습니다");
		 			}
		 			
		 		}, 
		 		error: function (e) { 
		 			console.log("ajax 에러발생");
		 		} 
		 	});
});

//모달 카드 파일 삭제
$(document).on('click','.card-modal-file-delete',function() {
	
	let fileNo = $(this).attr('fileno')
	
	$.ajax({
		url: "cardFilesDelete.ajax",
		data: {
				"fileNo": fileNo,
				"cardNo": cardNo,
				"teamNo": $('#teamNo').val()
				},
		dataType: "json",
		success: function(resData){
				
				//파일 목록 비우기
				$('#cardModalFileList').empty();
				
				//파일 목록 다시 뿌리기
				$.each(resData, function(index, item){
 					addUploadFileTag($('#cardModalFileList'), item);
 				});
				
				//파일 개수 재설정
				cardFilecount = resData.length;
			
				},
		error: function(request, status, error){
					alert(  "code:"+request.status
							+"\n"+"message:"+request.responseText
							+"\n"+"error:"+error);
				}
	});
	
});








//모달 리플 삭제 
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
					 $.ajax({ 
							url: "CardContentSelect.ajax",
							data: {
									cardNo: cardNo
									},
					        dataType: "json",
					        
							success: function(result) {
								console.log("딜리트 코멘트 카운트 셀렉트");
								var cardComment = result.commentCount;
								cardCommentcount = cardComment;

							} 
						});
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
    var display= $(this).parent().prev();

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




































//////////////////////////////////////// 드래그앤 드랍 ////////////////////////////////////////////

//혜정 짠것 
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//$(document).ready(function() {
//	var selectcard = "";
//	$('.kanban-card-list').on('mousedown', function(e) {
//		selectcard = $(this).outerHeight(true);
//		// console.log(selectcard)
//	});
//
//	const list_items = document.querySelectorAll('.kanban-card-list');
//	const lists = document.querySelectorAll('.divForDragNDrop');
//	let draggedItem = null;
//
//	for (let i = 0; i < list_items.length; i++) {
//		const item = list_items[i];
//
//		item.addEventListener('dragstart', function(e) {
//
//			draggedItem = item;
//
//			setTimeout(function() {
//				item.style.display = 'none';
//			}, 0);
//		});
//		item.addEventListener('dragend', function(h) {
//
//			setTimeout(function() {
//				draggedItem.style.display = 'block';
//				draggedItem = null;
//			}, 0);
//		})
//
//		for (let j = 0; j < lists.length; j++) {
//			const list = lists[j];
//
//			list.addEventListener('dragover', function(e) {
//				e.preventDefault();
//
//			})
//
//			list.addEventListener('dragenter', function() {
//				// e.preventDefault();
//				console.log('dragenter');
//				console.log(this.parentNode)
//				var cardlistheight = this.parentNode.offsetHeight;
//
////				this.style.height = cardlistheight + selectcard + 'px';
//				console.log(cardlistheight)
//			})
//			list.addEventListener('dragleave', function() {
//				// this.style.backgroundColor = 'rgba(0,0,0,0.5)';
//				var cardlistheight = this.parentNode.offsetHeight;
//				console.log(this)
//				this.style.height = 'auto';
//			})
//			list.addEventListener('drop', function() {
//				this.append(draggedItem);
//				this.style.height = 'auto';
//			});
//
//		}
//	}
//});




////////////////////////////////////////////////////////////////////////////////
