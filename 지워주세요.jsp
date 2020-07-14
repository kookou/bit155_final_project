<script>
package kr.or.bit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import kr.or.bit.dto.Board;

public class BoardDao {
   static DataSource ds;
   Connection conn = null;
   PreparedStatement pstmt = null;
   ResultSet rs = null;
   
   static {
      InitialContext ctx;
      try {
          ctx = new InitialContext();
          Context envctx= (Context)ctx.lookup("java:comp/env"); //기본설정
          ds =(DataSource)envctx.lookup("/jdbc/oracle");//context.xml 에서 name="jdbc/oracle"
      }catch (Exception e) {
         System.out.println("look up Fail : " + e.getMessage());
      }
   }

   //Board List가져오기
   public List<Board> selectBoardList(int cpage, int pageSize) {
      //cpage : 현재 보고싶은 페이지 번호
      //pageSize : 나누고싶은 페이지 사이즈
      List<Board> list = null;
      
      try {


sql=
	 "	select b.board_no, b.title, b.content, b.views, b.write_date, b.comment_count, b.refer, b.depth, b.step, b.all_board_list_no, b.id, u.NICKNAME, a.team_no from board_list b"
     "  inner join `user` u on b.id = u.id"
     "  inner join all_board_list a on b.all_board_list_no = a.all_board_list_no"
     "  where a.all_board_list_no=#{allBoardListNo} and a.team_no=#{teamNo}"
     "  order by b.board_no desc"
          
         conn = ds.getConnection();
         String sql = "select * " +
                   "from ( " + 
                   "   select rownum rn, board_num, board_name, board_subject, board_readcount, board_date, board_re_ref, board_re_lev, board_re_seq, board_file, " + 
                   "         (select count(*) from board_comment where board_num=b.board_num) as cnt" +
                   "   from ( " + 
                   "      SELECT * " +
                   "      FROM board " +
                   "      ORDER BY board_re_ref DESC, board_re_seq asc" +
                   "   ) b" +
                   "   where rownum <= ?" + //end row
                   ") " +
                   "where rn >= ?"; //start row
         pstmt = conn.prepareStatement(sql);
         
         //공식같은 로직
         //cpage=1, pageSize=5 일 경우
         int start = cpage * pageSize - (pageSize-1); // 1*5-(5-1) >> 1
         int end = cpage * pageSize; // 1*5 >> 5
         
         pstmt.setInt(1, end);
         pstmt.setInt(2, start);
         
         rs = pstmt.executeQuery();
         
         list = new ArrayList<Board>();
         while(rs.next()) {
            Board board = new Board();
            board.setBoardNum(rs.getInt("board_num"));
            board.setBoardName(rs.getString("board_name"));
            board.setBoardSubject(rs.getString("board_subject"));
            board.setBoardReadcount(rs.getInt("board_readcount"));
            board.setBoardDate(rs.getString("board_date"));
            board.setBoardFile(rs.getString("board_file"));
            board.setCommentCnt(rs.getInt("cnt")); //코맨트 갯수
            
            //계층형
            board.setBoardReRef(rs.getInt("board_re_ref"));
            board.setBoardReSeq(rs.getInt("board_re_seq"));
            board.setBoardReLev(rs.getInt("board_re_lev"));
            
            list.add(board);
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
      } finally {
         try {
            pstmt.close();
            rs.close();
            conn.close();
         } catch (Exception e2) {
            System.out.println(e2.getMessage());
         }
      }
      return list;
   }
   
   //게시물 총 건수
   public int totalBoardCount() {
      int totalCount = 0;
      
      try {
         conn = ds.getConnection();
         String sql = "select count(*) cnt from board";
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         if(rs.next()) {
            totalCount = rs.getInt("cnt");
         }
      } catch (Exception e) {
         e.getStackTrace();
      } finally {
         try {
            pstmt.close();
            rs.close();
            conn.close(); //반환하기
         } catch (Exception e2) {
            System.out.println(e2.getMessage());
         }
      }
      return totalCount;
   }
   
   //조회수 증가 함수
   public int updateReadcount(int boardNum) {
      int row = 0;
      
      try {
         conn = ds.getConnection();
         String sql = "update board set board_readcount = board_readcount+1 where board_num=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, boardNum);

         row = pstmt.executeUpdate();
      } catch (Exception e) {
         e.getStackTrace();
      } finally {
         try {
            pstmt.close();
            conn.close();
         } catch (Exception e2) {
            e2.getStackTrace();
         }
      }
      return row;
   }
   
   //게시판 상세보기
   public Board selectBoardByNo(int boardNum) {
      Board board = null;
      
      try {
         conn = ds.getConnection();
         String sql="select board_num, board_name, board_subject, board_content, board_file, board_readcount, board_date from board where board_num=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, boardNum);
         
         rs = pstmt.executeQuery();
         if(rs.next()) {
            board = new Board();
            board.setBoardNum(rs.getInt("board_num"));
            board.setBoardName(rs.getString("board_name"));
            board.setBoardSubject(rs.getString("board_subject"));
            board.setBoardContent(rs.getString("board_content"));
            board.setBoardFile(rs.getString("board_file"));
            board.setBoardReadcount(rs.getInt("board_readcount"));
            board.setBoardDate(rs.getString("board_date"));
         }
         
      } catch (Exception e) {
         System.out.println("사원검색에서 에러남: " + e.getMessage());
         e.getStackTrace();
      }finally {
         try {
            pstmt.close();
            conn.close();
            rs.close();//반환하기
         } catch (Exception e2) {
            
         }
      }
      
      return board;
   }
   
   //글쓰기 함수
   public int insertBoard(Board board) {
      int row = 0;
      int referMax = getMaxRefer();
      int refer = referMax + 1;
      
      try {
         conn = ds.getConnection();
         String sql = "insert into board(board_num, board_name, board_subject, board_content, board_date, board_readcount, board_file, board_re_ref)"
                  + "values(boar_num_seq.nextval, ?, ?, ?, sysdate, 0, ?, ?)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, board.getBoardName());
         pstmt.setString(2, board.getBoardSubject());
         pstmt.setString(3, board.getBoardContent());
         pstmt.setString(4, board.getBoardFile());
         pstmt.setInt(5, refer);
         
         row = pstmt.executeUpdate();
      } catch (Exception e) {
         e.getStackTrace();
         System.out.println("insert오류: " + e.getMessage());
      } finally {
         try {
            if (pstmt != null) try {pstmt.close();} catch(Exception e) {}
             if (conn != null) try {conn.close();} catch(Exception e) {}
         } catch (Exception e2) {
            e2.getStackTrace();
         }
      }

      return row;
   }
   
   //원본글 대한 refer 값 구하기
   public int getMaxRefer() {
      //select nvl(max(refer),0) from jspboard >> 처음 글 >> 0   >> refer + 1 값을   
      int maxRefer = 0;
      
      try {
         conn = ds.getConnection();
         String sql = "select nvl(max(board_re_ref), 0) from board";
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();
         if(rs.next()) {
            maxRefer = rs.getInt(1);
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }finally {
         try {
            pstmt.close();
            rs.close();
            conn.close();
         }catch (Exception e) {
            
         }
      }
      return maxRefer;
   }
   
   //게시글 (답글 쓰기) 
   public int insertReBoard(Board board) {
      //https://okky.kr/article/98829
      //답변형 게시판 참조..ㅠ 개어렵다
      
      //refer , step , depth 설정을 하려면 기존 정보(read 글)
      int result = 0;
      try {
         conn = ds.getConnection();
         
         int cnum = board.getBoardNum(); //현재 읽은 글의 글번호
         
         String name = board.getBoardName();
         String subject = board.getBoardSubject();
         String content = board.getBoardContent();
         String file = board.getBoardFile();
         
         //1. 답글 
         //현재 내가 읽은 글의 refer , depth , step
         String refer_depth_step_sal ="select board_re_ref, board_re_lev, board_re_seq from board where board_num=?";
         
         //2. 위치
         String step_sql = "select nvl(min(board_re_seq), 0) step from board where board_re_ref=? and board_re_seq > ? and board_re_lev <= ?";
         
         //답글  insert 
         String rewrite_sql="insert into board(board_num, board_name, board_subject, board_content, board_date, board_readcount, board_file, board_re_ref, board_re_lev, board_re_seq)" + 
                         " values(boar_num_seq.nextval, ?, ?, ?, sysdate, 0, ?, ?, ?, ?)";
         
         pstmt = conn.prepareStatement(refer_depth_step_sal);
         pstmt.setInt(1, cnum);
         rs = pstmt.executeQuery();
         
         if(rs.next()) { //데이터가 있다면, 원본글의 refer, step, depth가 존재한다면
            int refer = rs.getInt("board_re_ref");
            int depth = rs.getInt("board_re_lev");
            int step = rs.getInt("board_re_seq");
            
            pstmt = conn.prepareStatement(step_sql); //컴파일
            pstmt.setInt(1, refer); //원본글 ref
            pstmt.setInt(2, step); //원본글 step
            pstmt.setInt(3, depth); //원본글 lev
            rs = pstmt.executeQuery();
            if(rs.next()) {
               step = rs.getInt("step");
               if(step == 0) {
                  String maxStep = "select max(board_re_seq)+1 maxStep from board where board_re_ref=?";
                  pstmt = conn.prepareStatement(maxStep);
                  pstmt.setInt(1, refer); //원본글 ref
                  rs = pstmt.executeQuery();
                  if(rs.next()) {
                     step = rs.getInt("maxStep");
                  }
               } else {
                  String update_step = "update board set board_re_seq=board_re_seq+1 where board_re_ref=? and board_re_seq >= ? ";
                  pstmt = conn.prepareStatement(update_step);
                  pstmt.setInt(1, refer); //원본글 ref
                  pstmt.setInt(2, step);
                  pstmt.executeUpdate();
               }
            }
            
            
            //filename, filesize, refer, depth, step
            pstmt = conn.prepareStatement(rewrite_sql); //컴파일
            pstmt.setString(1, name);
            pstmt.setString(2, subject);
            pstmt.setString(3, content);
            pstmt.setString(4, file);
            
            //답변
            pstmt.setInt(5, refer);
            pstmt.setInt(6, depth+1); // 규칙 현재 읽은 글에 depth + 1
            pstmt.setInt(7, step);
            
            int row = pstmt.executeUpdate();
            if(row > 0) {
               result = row;
            }else {
               result = -1;
            }

         }
   
      } catch (Exception e) {
         e.printStackTrace();
      }finally {
         try {
            pstmt.close();
            rs.close();
            conn.close();//반환
         }catch (Exception e) {
            
         }
      }
      
      return result;
   }
   
   //글 삭제하기
   public int deleteBoard(int boardNum) {
      int row = 0;

      try {
         conn = ds.getConnection();
         String sql = "delete from board where board_num=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, boardNum);
         
         row = pstmt.executeUpdate();
      } catch (Exception e) {
         System.out.println("delete : " + e.getMessage());
      } finally {
         try {
            pstmt.close();
            conn.close();
         } catch (Exception e2) {
            System.out.println("delete : " + e2.getMessage());
         }
      }
      return row;
   }
   
   //게시글 수정 처리하기
   public int updateBoard(Board board) {
      int row = 0;
      
      try {
         conn = ds.getConnection();
         String sql = "update board set board_subject=?, board_content=?, board_file=? where board_num=?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, board.getBoardSubject());
         pstmt.setString(2, board.getBoardContent());
         pstmt.setString(3, board.getBoardFile());
         pstmt.setInt(4, board.getBoardNum());
         row = pstmt.executeUpdate();
      
      } catch (Exception e) {
         System.out.println("수정dao 에러: " + e.getMessage());
         e.getStackTrace();
      }finally {
         try {
            pstmt.close();
            conn.close();//반환
         } catch (Exception e2) {
            System.out.println(e2.getMessage());
         }
      }
   
      return row;
   }
}





<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
   <head>
      <title>Employee List</title>
      <meta charset="utf-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
      <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/main.css" />
      <link href="https://cdn.jsdelivr.net/npm/remixicon@2.4.0/fonts/remixicon.css" rel="stylesheet"> <!-- 아이콘 -->
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
   </head>
   <script type="text/javascript">
        $(function() {
            if("${requestScope.msg}" != "") {
                alert("${requestScope.msg}");
            }
        });
   </script>
   <body class="is-preload">
      <div id="page-wrapper">
         <!-- Header -->
         <jsp:include page="../../header.jsp"></jsp:include>

         <!-- Main -->
         <section id="main" class="container">
            <header>
               <h2>Board List</h2>
               <p>보람삼조 게시판</p>
            </header>
            
            <div class="row">
               <div class="col-12">

                  <!-- Table -->
                  <section class="box">
                     
                     <div class="table-wrapper">
                        <table>
                           <thead>
                              <tr>
                                 <th>글번호</th>
                                 <th>작성자</th>
                                 <th>글제목</th>
                                 <th>게시일</th>
                                 <th>조회수</th>
                              </tr>
                           </thead>
                           <tbody>
                              <c:forEach var="board" items="${requestScope.boardList}">
                                 <tr>
                                    <td>${board.boardNum}</td>
                                    <td>${board.boardName}</td>
                                    <td>
                                       <c:forEach var="i" begin="1" end="${board.boardReLev}">
                               			  &nbsp;&nbsp;&nbsp;
                             			 </c:forEach>
                              <!-- depth가 0보다 큰것은 답글. 답글인 경우에는 이미지를 붙여준다 -->
                              <c:if test="${board.boardReLev > 0}">
                                 <i class="ri-arrow-right-s-line"></i>
                              </c:if>
                                       <a href="selectBoard.board?boardNum=${board.boardNum}">${board.boardSubject}</a>
                              <!-- boardFile이 존재하는 경우에는 이미지를 붙여준다 -->
                                       <c:if test="${!empty board.boardFile}">
                                          <i class="ri-image-line"></i>
                                       </c:if>
                              <!-- comment cnt가 존재하는 경우 -->
                                       <c:if test="${board.commentCnt > 0}">
                                          [${board.commentCnt}]
                                       </c:if>
                                    </td>
                                    <td>${board.boardDate}</td>
                                    <td>${board.boardReadcount}</td>
                                 </tr>
                              </c:forEach>
                           </tbody>
                        </table>
                        
                        <!-- 페이징 -->
                        <!--이전 링크 --> 
                        <c:choose>
                     <c:when test="${requestScope.cpage > 1}">
                        <a href="boardList.board?cp=${requestScope.cpage-1}&ps=${requestScope.pageSize}" class="button alt small">&lt;</a>
                     </c:when>
                     <c:otherwise>
                        <a href="#" class="button alt small">&lt;</a>
                     </c:otherwise>
                        </c:choose>
                  <!-- page 목록 나열하기 -->
                  <c:forEach var="i" begin="1" end="${requestScope.pageCount}" step="1">
                     <c:choose>
                        <c:when test="${requestScope.cpage == i}">
                           <a href="#" class="button small">${i}</a>
                        </c:when>
                        <c:otherwise>
                           <a href="boardList.board?cp=${i}&ps=${requestScope.pageSize}" class="button alt small">${i}</a>
                        </c:otherwise>
                     </c:choose>
                  </c:forEach>
                  <!--다음 링크 -->
                  <c:choose>
                     <c:when test="${requestScope.cpage < requestScope.pageCount}">
                        <a href="boardList.board?cp=${requestScope.cpage+1}&ps=${requestScope.pageSize}" class="button alt small">&gt;</a>
                     </c:when>
                     <c:otherwise>
                        <a href="#" class="button alt small">&gt;</a>
                     </c:otherwise>
                        </c:choose>
                     </div>
                     <br>
                     <a href="<%=request.getContextPath()%>/insertBoardForm.board" class="button alt" id="writeBtn">글쓰기</a>
                  </section>

               </div>
            </div>
            
         </section>

         <!-- Footer -->
         <jsp:include page="../../footer.jsp"></jsp:include>
      </div>

      <!-- Scripts -->
      <script src="<%=request.getContextPath()%>/assets/js/jquery.min.js"></script>
      <script src="<%=request.getContextPath()%>/assets/js/jquery.dropotron.min.js"></script>
      <script src="<%=request.getContextPath()%>/assets/js/jquery.scrollex.min.js"></script>
      <script src="<%=request.getContextPath()%>/assets/js/browser.min.js"></script>
      <script src="<%=request.getContextPath()%>/assets/js/breakpoints.min.js"></script>
      <script src="<%=request.getContextPath()%>/assets/js/util.js"></script>
      <script src="<%=request.getContextPath()%>/assets/js/main.js"></script>
      <script type="text/javascript">
      //console.log('${sessionScope.admin}');
      //관리자 계정으로만 접근가능
      if('${sessionScope.admin}' == "") {
         $('#writeBtn').attr('href', '#').click(function() {
            alert('관리자 로그인 후 이용가능합니다.');
         });
      }
      </script>
      
   </body>
</html>


</script>