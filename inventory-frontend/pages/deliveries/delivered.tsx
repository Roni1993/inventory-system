import type {NextPage} from 'next'
import {useState} from "react";
import {
  DeliveryProperties,
  Direction,
  useBetweenDatesQuery,
  useDeliveriesQuery
} from "../../components/queries.generated";
import Pagination from "../../components/pagination";
import Table from "../../components/table";

const Delivered: NextPage = () => {
  const [page, setPage] = useState(0);
  const [result] = useBetweenDatesQuery({variables: {
      from: "2022-08-20T17:44:08.000Z",
      to: "2022-10-20T17:44:08.000Z",
      page: {page, size: 20, sort: [{direction: Direction.Asc, property:DeliveryProperties.DeliveryId}]}
  }})
  const { data, fetching, error } = result;

  if (fetching) return <p>Loading...</p>;
  if (error) return <p>Oh no... {error.message}</p>;

  const deliveries = data?.deliveredBetween
  return (
    <div>
      <Pagination page={page} setPage={setPage} totalPages={deliveries?.totalPages || 1}></Pagination>
      <Table deliveries={deliveries}></Table>
    </div>
  )
}

export default Delivered
