import gql from 'graphql-tag';
import * as Urql from 'urql';
export type Maybe<T> = T | null;
export type InputMaybe<T> = Maybe<T>;
export type Exact<T extends { [key: string]: unknown }> = { [K in keyof T]: T[K] };
export type MakeOptional<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]?: Maybe<T[SubKey]> };
export type MakeMaybe<T, K extends keyof T> = Omit<T, K> & { [SubKey in K]: Maybe<T[SubKey]> };
export type Omit<T, K extends keyof T> = Pick<T, Exclude<keyof T, K>>;
/** All built-in and custom scalars, mapped to their actual values */
export type Scalars = {
  ID: string;
  String: string;
  Boolean: boolean;
  Int: number;
  Float: number;
  DateTime: any;
};

export type Delivery = {
  __typename?: 'Delivery';
  actualDeliveryDate?: Maybe<Scalars['DateTime']>;
  category: DeliveryCategory;
  id: Scalars['ID'];
  items: Array<Item>;
  plannedDeliveryDate: Scalars['DateTime'];
  status: DeliveryStatus;
};

export enum DeliveryCategory {
  Parcel = 'PARCEL',
  Truck = 'TRUCK'
}

export type DeliveryCollection = {
  __typename?: 'DeliveryCollection';
  content: Array<Delivery>;
  size: Scalars['Int'];
  totalElements: Scalars['Int'];
  totalPages: Scalars['Int'];
};

export enum DeliveryProperties {
  ActualDeliveryDate = 'actualDeliveryDate',
  Category = 'category',
  DeliveryId = 'deliveryId',
  PlannedDeliveryDate = 'plannedDeliveryDate',
  Status = 'status'
}

export enum DeliveryStatus {
  Delivered = 'DELIVERED',
  InProgress = 'IN_PROGRESS',
  Planned = 'PLANNED',
  Unknown = 'UNKNOWN'
}

export enum Direction {
  Asc = 'ASC',
  Desc = 'DESC'
}

export type Item = {
  __typename?: 'Item';
  name: Scalars['String'];
  quantity: Scalars['Int'];
};

export type PageInput = {
  page?: InputMaybe<Scalars['Int']>;
  size?: InputMaybe<Scalars['Int']>;
  sort?: InputMaybe<Array<InputMaybe<Sort>>>;
};

export type Query = {
  __typename?: 'Query';
  deliveredBetween: DeliveryCollection;
  deliveries: DeliveryCollection;
  deliveriesPlannedTomorrow: DeliveryCollection;
  delivery?: Maybe<Delivery>;
};


export type QueryDeliveredBetweenArgs = {
  from: Scalars['DateTime'];
  page?: InputMaybe<PageInput>;
  to: Scalars['DateTime'];
};


export type QueryDeliveriesArgs = {
  page?: InputMaybe<PageInput>;
};


export type QueryDeliveriesPlannedTomorrowArgs = {
  page?: InputMaybe<PageInput>;
};


export type QueryDeliveryArgs = {
  id: Scalars['ID'];
};

export type Sort = {
  direction?: InputMaybe<Direction>;
  property?: InputMaybe<DeliveryProperties>;
};

export type BetweenDatesQueryVariables = Exact<{
  from: Scalars['DateTime'];
  to: Scalars['DateTime'];
  page?: InputMaybe<PageInput>;
}>;


export type BetweenDatesQuery = { __typename?: 'Query', deliveredBetween: { __typename?: 'DeliveryCollection', size: number, totalElements: number, totalPages: number, content: Array<{ __typename?: 'Delivery', id: string, status: DeliveryStatus, category: DeliveryCategory, plannedDeliveryDate: any, actualDeliveryDate?: any | null, items: Array<{ __typename?: 'Item', name: string, quantity: number }> }> } };

export type DeliveriesQueryVariables = Exact<{
  page?: InputMaybe<PageInput>;
}>;


export type DeliveriesQuery = { __typename?: 'Query', deliveries: { __typename?: 'DeliveryCollection', size: number, totalElements: number, totalPages: number, content: Array<{ __typename?: 'Delivery', id: string, status: DeliveryStatus, category: DeliveryCategory, plannedDeliveryDate: any, actualDeliveryDate?: any | null, items: Array<{ __typename?: 'Item', name: string, quantity: number }> }> } };

export type TomorrowQueryVariables = Exact<{ [key: string]: never; }>;


export type TomorrowQuery = { __typename?: 'Query', deliveriesPlannedTomorrow: { __typename?: 'DeliveryCollection', size: number, totalElements: number, totalPages: number, content: Array<{ __typename?: 'Delivery', id: string, status: DeliveryStatus, category: DeliveryCategory, plannedDeliveryDate: any, actualDeliveryDate?: any | null, items: Array<{ __typename?: 'Item', name: string, quantity: number }> }> } };

export type DeliveryQueryVariables = Exact<{
  id: Scalars['ID'];
}>;


export type DeliveryQuery = { __typename?: 'Query', delivery?: { __typename?: 'Delivery', id: string, status: DeliveryStatus, category: DeliveryCategory, plannedDeliveryDate: any, actualDeliveryDate?: any | null, items: Array<{ __typename?: 'Item', name: string, quantity: number }> } | null };


export const BetweenDatesDocument = gql`
    query betweenDates($from: DateTime!, $to: DateTime!, $page: PageInput) {
  deliveredBetween(from: $from, to: $to, page: $page) {
    content {
      id
      status
      category
      plannedDeliveryDate
      actualDeliveryDate
      items {
        name
        quantity
      }
    }
    size
    totalElements
    totalPages
  }
}
    `;

export function useBetweenDatesQuery(options: Omit<Urql.UseQueryArgs<BetweenDatesQueryVariables>, 'query'>) {
  return Urql.useQuery<BetweenDatesQuery, BetweenDatesQueryVariables>({ query: BetweenDatesDocument, ...options });
};
export const DeliveriesDocument = gql`
    query deliveries($page: PageInput) {
  deliveries(page: $page) {
    content {
      id
      status
      category
      plannedDeliveryDate
      actualDeliveryDate
      items {
        name
        quantity
      }
    }
    size
    totalElements
    totalPages
  }
}
    `;

export function useDeliveriesQuery(options?: Omit<Urql.UseQueryArgs<DeliveriesQueryVariables>, 'query'>) {
  return Urql.useQuery<DeliveriesQuery, DeliveriesQueryVariables>({ query: DeliveriesDocument, ...options });
};
export const TomorrowDocument = gql`
    query tomorrow {
  deliveriesPlannedTomorrow {
    content {
      id
      status
      category
      plannedDeliveryDate
      actualDeliveryDate
      items {
        name
        quantity
      }
    }
    size
    totalElements
    totalPages
  }
}
    `;

export function useTomorrowQuery(options?: Omit<Urql.UseQueryArgs<TomorrowQueryVariables>, 'query'>) {
  return Urql.useQuery<TomorrowQuery, TomorrowQueryVariables>({ query: TomorrowDocument, ...options });
};
export const DeliveryDocument = gql`
    query delivery($id: ID!) {
  delivery(id: $id) {
    id
    status
    category
    plannedDeliveryDate
    actualDeliveryDate
    items {
      name
      quantity
    }
  }
}
    `;

export function useDeliveryQuery(options: Omit<Urql.UseQueryArgs<DeliveryQueryVariables>, 'query'>) {
  return Urql.useQuery<DeliveryQuery, DeliveryQueryVariables>({ query: DeliveryDocument, ...options });
};