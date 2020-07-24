select group_team_no, g.group_no, t.team_no, g.id, team_name, background_color
  from `group_team` gt
  join `group` g
	on gt.group_no = g.group_no
  join `team` t
	on gt.team_no = t.team_no
  join `team_member` tm
    on t.TEAM_NO = tm.TEAM_NO
 where g.id= 'hrin@3004.com';