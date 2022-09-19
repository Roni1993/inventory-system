import type {NextPage} from 'next'
import {useTomorrowQuery} from "../../components/queries.generated";
import Table from "../../components/table";

const Tomorrow: NextPage = () => {
  const [result] = useTomorrowQuery()
  const { data, fetching, error } = result;

  if (fetching) return <p>Loading...</p>;
  if (error) return <p>Oh no... {error.message}</p>;

  const deliveries = data?.deliveriesPlannedTomorrow
  return (
    <div>
      <Table deliveries={deliveries}></Table>
    </div>
  )
}

export default Tomorrow
