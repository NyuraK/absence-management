INSERT INTO `absence_management`.`users` (`users_id`, `email`, `hire_date`, `integrated`, `login`, `name`, `password`, `role`, `surname`, `rest_days`) VALUES ('1', 'repo@key-mail.net', '2013-06-14 22:00:00.000000',0,'root','admin','$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe','ADMIN','admin', '28');
INSERT INTO `absence_management`.`users` (`users_id`, `email`, `hire_date`, `integrated`, `login`, `name`, `password`, `role`, `surname`, `rest_days`) VALUES ('2', 'repo@key-mail.net', '2014-06-14 22:00:00.000000',0,'director','Director','$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe','DIRECTOR','1', '28');

INSERT INTO `absence_management`.`teams` (`teams_id`, `name`, `absence_quota`, `managers_id`) VALUES ('1', 'Department Managers', '2', '2');

INSERT INTO `absence_management`.`users` (`users_id`, `email`, `hire_date`, `integrated`, `login`, `name`, `password`, `role`, `surname`, `rest_days`, `teams_id`) VALUES ('3', 'repo@key-mail.net','2015-06-14 22:00:00.000000',0,'manager1','Department_1','$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe','MANAGER','Manager', '28','1');
INSERT INTO `absence_management`.`users` (`users_id`, `email`, `hire_date`, `integrated`, `login`, `name`, `password`, `role`, `surname`, `rest_days`, `teams_id`) VALUES ('4', 'repo@key-mail.net','2015-06-14 22:00:00.000000',0,'manager2','Department_2','$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe','MANAGER','Manager', '28','1');

INSERT INTO `absence_management`.`departments` (`departments_id`, `name`, `directors_id`) VALUES ('1', 'Department 1', '3');
INSERT INTO `absence_management`.`departments` (`departments_id`, `name`, `directors_id`) VALUES ('2', 'Department 2', '4');

INSERT INTO `absence_management`.`teams` (`teams_id`, `name`, `absence_quota`, `departments_id`, `managers_id`) VALUES ('2', 'Team 1', '2', '1', '3');

INSERT INTO `absence_management`.`users` (`users_id`, `email`, `hire_date`, `integrated`, `login`, `name`, `password`, `role`, `surname`, `rest_days`, `teams_id`) VALUES ('5', 'repo@key-mail.net','2015-06-14 22:00:00.000000',0,'manager3','Manager','$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe','MANAGER','3', '28','2');

INSERT INTO `absence_management`.`teams` (`teams_id`, `name`, `absence_quota`, `departments_id`, `managers_id`) VALUES ('3', 'Team 2', '2', '1', '5');
INSERT INTO `absence_management`.`teams` (`teams_id`, `name`, `absence_quota`, `departments_id`, `managers_id`) VALUES ('4', 'Team 3', '2', '2', '4');

INSERT INTO `absence_management`.`users` (`users_id`, `email`, `hire_date`, `integrated`, `login`, `name`, `password`, `role`, `surname`, `rest_days`, `teams_id`) VALUES ('6', 'repo@key-mail.net', '2016-06-14 22:00:00.000000',0,'employee1','Employee','$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe','EMPLOYEE','1', '28', '2');
INSERT INTO `absence_management`.`users` (`users_id`, `email`, `hire_date`, `integrated`, `login`, `name`, `password`, `role`, `surname`, `rest_days`, `teams_id`) VALUES ('7', 'repo@key-mail.net', '2016-06-14 22:00:00.000000',0,'employee2','Employee','$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe','EMPLOYEE','2', '28', '3');
INSERT INTO `absence_management`.`users` (`users_id`, `email`, `hire_date`, `integrated`, `login`, `name`, `password`, `role`, `surname`, `rest_days`, `teams_id`) VALUES ('8', 'repo@key-mail.net', '2016-06-14 22:00:00.000000',0,'employee3','Employee','$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe','EMPLOYEE','3', '28', '3');
INSERT INTO `absence_management`.`users` (`users_id`, `email`, `hire_date`, `integrated`, `login`, `name`, `password`, `role`, `surname`, `rest_days`, `teams_id`) VALUES ('9', 'repo@key-mail.net', '2016-06-14 22:00:00.000000',0,'employee4','Employee','$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe','EMPLOYEE','4', '28', '4');
INSERT INTO `absence_management`.`users` (`users_id`, `email`, `hire_date`, `integrated`, `login`, `name`, `password`, `role`, `surname`, `rest_days`, `teams_id`) VALUES ('10', 'repo@key-mail.net', '2016-06-14 22:00:00.000000',0,'employee5','Employee','$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe','EMPLOYEE','5', '28', '4');
INSERT INTO `absence_management`.`users` (`users_id`, `email`, `hire_date`, `integrated`, `login`, `name`, `password`, `role`, `surname`, `rest_days`, `teams_id`) VALUES ('11', 'repo@key-mail.net', '2016-06-14 22:00:00.000000',0,'employee6','Employee','$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe','EMPLOYEE','6', '28', '4');

INSERT INTO `absence_management`.`types_of_request` (`type_of_requests_id`, `is_influencing_on_vr`, `name`, `does_need_approval`) VALUES ('1', 1, 'Sick leave', 0);
INSERT INTO `absence_management`.`types_of_request` (`type_of_requests_id`, `is_influencing_on_vr`, `name`, `does_need_approval`) VALUES ('2', 1, 'Vacation', 1);
INSERT INTO `absence_management`.`types_of_request` (`type_of_requests_id`, `is_influencing_on_vr`, `name`, `does_need_approval`) VALUES ('3', 1, 'Child care',0);
INSERT INTO `absence_management`.`types_of_request` (`type_of_requests_id`, `is_influencing_on_vr`, `name`, `does_need_approval`) VALUES ('4', 0, 'Business trip', 0);
INSERT INTO `absence_management`.`types_of_request` (`type_of_requests_id`, `is_influencing_on_vr`, `name`, `does_need_approval`) VALUES ('5', 0, 'Remote work', 1);
