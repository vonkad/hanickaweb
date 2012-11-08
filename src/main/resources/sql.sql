select users.username,types_groups.typeid,files.filename
     from users
     inner join users_groups on (users_groups.userid=users.id)
     inner join types_groups on (types_groups.groupid=users_groups.groupid)
     left join files on (users.id=files.userid and files.current=1 and files.typeid=types_groups.typeid)
     where users_groups.groupid=3
     order by users.username;

select types.id,types.title
    from types
    inner join types_groups on (types.id=types_groups.typeid)
    where types_groups.groupid=2
    order by types.id;

select id,title from groups;

select users.id,ugd.klic,ugd.hodnota
  from users
  inner join users_groups on (users_groups.userid=users.id)
  inner join user_group_data as ugd on (ugd.userid=users.id and ugd.groupid=users_groups.groupid)
  where users_groups.groupid=3;

select klice.klic,klice.title
from klice
inner join klice_groups on (klice.klic=klice_groups.klicid)
where klice_groups.groupid=3
order by klice.klic;