CREATE TABLE House (
    houseId serial primary key ,
    userId int not null ,
    rent int not null ,
    numberOfRooms int not null,
    locality varchar(400) not null,
    CONSTRAINT fk_ownerId Foreign Key (userId)
        REFERENCES Users (userId)
);