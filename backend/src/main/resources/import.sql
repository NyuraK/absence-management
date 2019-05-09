INSERT INTO `absence_management`.`users` (`users_id`, `family_name`, `hire_date`, `login`, `name`, `password`, `rest_days`, `role`, `surname`) VALUES ('1', 'root', '2019-01-01', 'root', 'root', '$2a$10$2E5DGKkXVLeJxeD7h1IBM.Mf57L8q5amu720xbvDknu7D2.Sh6wPe', '0', 'ADMIN', 'root');
INSERT INTO `absence_management`.`types_of_request` (`type_of_requests_id`, `is_influencing_on_vr`, `name`, `does_need_approval`) VALUES ('1', 1, 'Sick leave', 0);
INSERT INTO `absence_management`.`types_of_request` (`type_of_requests_id`, `is_influencing_on_vr`, `name`, `does_need_approval`) VALUES ('2', 1, 'Vacation', 1);
INSERT INTO `absence_management`.`types_of_request` (`type_of_requests_id`, `is_influencing_on_vr`, `name`, `does_need_approval`) VALUES ('3', 1, 'Child care',0);
INSERT INTO `absence_management`.`types_of_request` (`type_of_requests_id`, `is_influencing_on_vr`, `name`, `does_need_approval`) VALUES ('4', 0, 'Business trip', 0);
INSERT INTO `absence_management`.`types_of_request` (`type_of_requests_id`, `is_influencing_on_vr`, `name`, `does_need_approval`) VALUES ('5', 0, 'Remote work', 1);
