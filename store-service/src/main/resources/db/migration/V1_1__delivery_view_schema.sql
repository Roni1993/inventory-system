create table delivery_view
(
    delivery_id           varchar(255) not null,
    actual_delivery_date  timestamp,
    category              varchar(255),
    items                 jsonb,
    planned_delivery_date timestamp,
    status                varchar(255),
    primary key (delivery_id)
);
