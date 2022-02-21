-- v1.4
insert into game_db.public.task_template (task_type_id, general_template)
    values (1, '{ "max_number": [70, 50, 30], "arg": [["*", "+"], ["+", "-"], ["+", "-"]] }');
insert into game_db.public.task_template (task_type_id, general_template)
    values (2, '{ "min_char_count": 15, "max_char_count": 30 }');
insert into game_db.public.task_template (task_type_id, general_template)
    values (3, '{ "min_seq_length": 3, "max_seq_length": 8 }');
