(select b.board_no, title, content, views, date_format(write_date, '%Y-%m-%d') as write_date, refer, depth, step, b.all_board_list_no, b.id, nickname, team_no, board_noti,
	   (select count(*) from board_comment where board_no=b.board_no) as comment_count,
       (select count(*) from board_file where board_no=b.board_no) as file_count
  from board_list b
 inner join `user` u
    on u.id = b.id
 inner join all_board_list a
    on a.all_board_list_no = b.all_board_list_no
 where a.all_board_list_no = 1
   and board_noti = 'Y'
 order by b.board_no desc)
union all
(select b.board_no, title, content, views, date_format(write_date, '%Y-%m-%d') as write_date, refer, depth, step, b.all_board_list_no, b.id, nickname, team_no, board_noti,
       (select count(*) from board_comment where board_no=b.board_no) as comment_count,
       (select count(*) from board_file where board_no=b.board_no) as file_count
  from (select * from board_list) b
 inner join `user` u
    on u.id = b.id
 inner join all_board_list a
    on a.all_board_list_no = b.all_board_list_no
 where a.all_board_list_no = 1
 order by refer desc, step asc);