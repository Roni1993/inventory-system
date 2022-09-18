create table delivery_event
(
    event_id              uuid not null,
    actual_delivery_date  timestamp,
    category              varchar(255),
    created               timestamp,
    delivery_id           varchar(255),
    items                 jsonb,
    occurred              timestamp,
    planned_delivery_date timestamp,
    status                varchar(255),
    primary key (event_id)
);