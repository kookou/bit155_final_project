<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Vue.js CDN -->
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<!-- 엑시오스 CDN (ajax같은 것) -->
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>

<div id="app">
    <div class="page-wrapper">
        <!-- ============================================================== -->
        <!-- Container fluid  -->
        <!-- ============================================================== -->
        <div class="container-fluid">
			${todoList}
            <!-- ============================================================== -->
            <!-- Start Page Content -->
            <!-- ============================================================== -->
            <div class="row">
                <!-- <component :is="todoLists"></component> -->
                <component v-for="item in todoLists" :is="item"></component>
                
                <div class="col-xl-4">
                    <div class="btn waves-effect waves-light btn-primary" id="addTodoListBtn" @click="addTodoTitle" v-if="addListBtnisStatusOn">+ Add Todo-List</div>
                </div>

            </div>
            <!-- end row-->

        </div>
        <!-- ============================================================== -->
        <!-- End Container fluid  -->
        <!-- ============================================================== -->
    </div>
<!-- ============================================================== -->
<!-- End Page wrapper  -->
<!-- ============================================================== -->
</div>

<script>
    var todoListComponent = {
        template: `
            <div class="col-xl-4">
                <div class="card">
                    <div class="card-body">
                        <input type="text" class="form-control" placeholder="Title 입력 후 Enter" v-model="title" @keyup.enter="addTodoList" v-if="isStatusOn"/>
                        <div v-if="isStatusOff">
                            <h4 class="card-title mb-3">{{ title }}</h4>
                            <div style="width: 100%;">
                                <input type="text" class="form-control" placeholder="할 일을 입력하세요" v-model="userInput" @keyup.enter="addNewTodo"/>
                            </div>
                            <br>

                            <div class="text-right">
                                <button type="button" class="btn btn-outline-warning" @click="changeCurrentState('all')"><i class="fa fa-heart"></i> All</button>
                                <button type="button" class="btn btn-outline-danger" @click="changeCurrentState('active')"><i class="far fa-heart"></i> Active</button>
                                <button type="button" class="btn btn-outline-success" @click="changeCurrentState('done')"><i class="fa fa-check"></i> Done</button>
                            </div>
                            <br>
                            
                            <div class="tab-content">
                                <template v-for="todo in activeTodoList">
                                    <div class="tab-pane show active">
                                        <div class="custom-control custom-checkbox">
                                            <div style="float: left;">
                                                <input type="checkbox" class="custom-control-input" :id="todo.content" :value="todo.content" 
                                                    @click="toggleTodoState(todo)" :checked="todo.state == 'done' ? true : false">
                                                <label class="custom-control-label" :for="todo.content" :style="{color:todo.state == 'done' ? 'lightgray' : '#7c8798'}">{{ todo.content }}</label>
                                            </div>
                                            <div style="float: right;">
                                                <a href="#"><i class="fas fa-pencil-alt iconStyle"></i></a>&nbsp
                                                <a href="#"><i class="fas fa-trash-alt iconStyle"></i></a>
                                            </div>
                                        </div>
                                    </div>
                                </template>
                            </div>
                        </div>
                    </div> <!-- end card-body-->
                </div> <!-- end card-->
            </div> <!-- end col -->
        `,
        data() {
            return {
                title: '',
                userInput: '',
                todoList: [],
                currentState: 'all', //출력할 상태값을 가질 변수
                isStatusOn: true,
                isStatusOff: false
            };
        },
        //클릭하면 값 들어오는거까지는 했는데...ㅠ 어렵다..
        created() {
        	axios.get('todoList2.do?teamNo=1')
            .then((response) => {
                for(var s in response.data) {
	                console.log(response.data[s].content); // 객체 형태로 반환. 파싱작업 불필요
	                this.todoList.push({
	                    content: response.data[s].content,
	                    state: 'active' //완료하지 않은 항목
	                });
                }
            });
        },
        methods: {//상태값을 변경할 메소드
            changeCurrentState(state) {
                this.currentState = state;
            },
            addNewTodo() {
                this.todoList.push({
                    content: this.userInput,
                    state: 'active' //완료하지 않은 항목
                });
                this.userInput = '';
            },
            toggleTodoState(todo) { // 클릭시 상태값을 변경할 함수 //todo parameter는 클릭한 항목을 받을 parameter
                //parameter로 받은 todo의 state를 done과 active로 번갈아가며 설정할 수 있도록 함
                todo.state = todo.state === 'active' ? 'done' : 'active';
            },
            addTodoList() {
                this.isStatusOn = false;
                this.isStatusOff = true;
                this.$root.addListBtnisStatusOn = true;

                //axios통신으로 todoList Title DB에 insert하기
	            var params = new URLSearchParams();
	            params.append('title', this.title);
	            params.append('teamNo', 1);
	            params.append('id', 'hyerin');
	            axios.post('insertTodoTitle.do', params
	    	    ).then(response => {
	                console.log(response);
	            }).catch((ex) => {
	                console.log("ERROR!!!!! : ",ex);
	            });
            }
        },
        // computed에 선언해놓은 것은 내부변수처럼 불러서 사용할 수 있기 때문에 activeTodoList() 이렇게 부르는 것이 아니라 activeTodoList일케 부름..
        // 클래스의 getter함수처럼 동작한다고 한다
        computed: {
            activeTodoList() {
                //currentState가 all이면 모든항목을 가져오고, 아닐경우 currentState과 동일한 항목만 가져오도록 설정
                return this.todoList.filter(todo => this.currentState === 'all' || todo.state === this.currentState);
            }
        },
    };

    
    
    var app = new Vue({
        el: '#app',
        data() {
            return {
                todoLists : [],
                addListBtnisStatusOn: true
            };
        },
        /*
        mounted() {
        	axios.get('todoList2.do?teamNo=1')
            .then(function(response) {
                console.log(response.data); // 객체 형태로 반환. 파싱작업 불필요
                this.todoLists = response.data;
            });
        },
        */
        methods: {
            /*
        	list: function() {
            	axios.get('todoList2.do?teamNo=1')
                .then(function(response) {
                    console.log(response.data.todoList); // 객체 형태로 반환. 파싱작업 불필요
                });
            },
            */
            addTodoTitle() {
                this.todoLists.push('todoList');
                this.addListBtnisStatusOn = false;
            }
        },
        components: {
            'todoList': todoListComponent
        }
    });
	/*
    axios.get('todoList2.do?teamNo=1')
    .then(function(response) {
        console.log(response.data); // 객체 형태로 반환. 파싱작업 불필요
    });
    */
</script>