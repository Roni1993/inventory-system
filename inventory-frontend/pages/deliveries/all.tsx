import type {NextPage} from 'next'
import {DeliveryProperties, Direction, useDeliveriesQuery} from "../../components/queries.generated";
import Table from '../../components/table';
import Pagination from "../../components/pagination";
import {useState} from "react";

const All: NextPage = () => {
  const [page, setPage] = useState(0);
  const [result] = useDeliveriesQuery({variables: {page:
        {page, size: 20, sort: [{direction: Direction.Asc, property:DeliveryProperties.DeliveryId}]} }})
  const { data, fetching, error } = result;

  if (fetching) return <p>Loading...</p>;
  if (error) return <p>Oh no... {error.message}</p>;

  const deliveries = data?.deliveries
  return (
    <div>
      <Pagination page={page} setPage={setPage} totalPages={deliveries?.totalPages || 1}></Pagination>
      <Table deliveries={deliveries}></Table>
    </div>
  )
}

export default All
