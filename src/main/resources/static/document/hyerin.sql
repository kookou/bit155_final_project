select group_team_no, g.group_no, t.team_no, g.id, team_name, background_color
  from `group_team` gt
  join `group` g
	on gt.group_no = g.group_no
  join `team` t
	on gt.team_no = t.team_no
  join `team_member` tm
    on t.TEAM_NO = tm.TEAM_NO
 where g.id= 'hrin@3004.com';
 
select *
  from kanban_list k
  join all_board_list a
    on k.ALL_BOARD_LIST_NO  = a.ALL_BOARD_LIST_NO
 group by `name`
 having k.all_board_list_no = 58;
 
select * from timeline where dml_kind='wthdr team';