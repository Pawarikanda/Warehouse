# --- Initialize tags
# --- !Ups

insert into tag(Tid,name) values (1, 'lightweight');
insert into tag(Tid,name) values (2, 'metal');
insert into tag(Tid,name) values (3, 'plastic');

insert into Product values(2,2222222222222,'Paperclips 2','Description Paperclip');
insert into Product values(3,3333333333333,'Paperclips 3','Description Paperclip');
insert into Product values(4,4444444444444,'Paperclips 4','Description Paperclip');
insert into Product values(1,1111111111111,'Paperclips 1','Description Paperclip');
insert into Product values(5,5555555555555,'Paperclips 5','Description Paperclip');
insert into Product values(6,6666666666666,'Paperclips 6','Description Paperclip');
insert into Product values(7,7777777777777,'Paperclips 7','Description Paperclip');
insert into Product values(8,8888888888888,'Paperclips 8','Description Paperclip');
insert into Product values(9,9999999999999,'Paperclips 9','Description Paperclip');
insert into Product values(10,111111111111,'Paperclips 1','Description Paperclip');
insert into Product values(11,888888888888,'Paperclips 8','Description Paperclip');

insert into User values(1,'exalt','exalt');
insert into User values(2,'anotida','anotida');
insert into User values(3,'petros','petros');

# --- !Downs
SET REFERENTIAL_INTEGRITY FALSE;
delete from tag;
delete from Product;
delete from User;