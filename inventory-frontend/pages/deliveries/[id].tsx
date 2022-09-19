import type {NextPage} from 'next'
import {DeliveryProperties, Direction, useDeliveriesQuery, useDeliveryQuery} from "../../components/queries.generated";
import Table from '../../components/table';
import Link from "next/link";
import {useRouter} from "next/router";

const All: NextPage = () => {
  const router = useRouter()
  const id = router.query.id as string
  const [result] = useDeliveryQuery({variables: {id: id}})
  const {data, fetching, error} = result;

  if (fetching) return <p>Loading...</p>;
  if (error) return <p>Oh no... {error.message}</p>;

  const delivery = result.data?.delivery
  return (
    <div className="flex flex-col">
      <div className="stats shadow mb-10">
        <div className="stat place-items-center">
          <div className="stat-title">Delivery ID</div>
          <div className="stat-value">{delivery?.id}</div>
        </div>
        <div className="stat place-items-center">
          <div className="stat-title">Category</div>
          <div className="stat-value text-secondary">{delivery?.category}</div>
        </div>
        <div className="stat place-items-center">
          <div className="stat-title">Status</div>
          <div className="stat-value">{delivery?.status}</div>
        </div>
      </div>

      <div className="stats shadow mb-10">
        <div className="stat place-items-center">
          <div className="stat-title">Planned Delivery Date</div>
          <div className="stat-desc">{delivery?.plannedDeliveryDate}</div>
        </div>
        <div className="stat place-items-center">
          <div className="stat-title">	Actual Delivery Date</div>
          <div className="stat-desc text-secondary">{delivery?.actualDeliveryDate}</div>
        </div>
      </div>

      <div className="overflow-x-auto">
        <h1 className="text-4xl mb-2">Items</h1>
        <table className="table w-full">
          <thead>
          <tr>
            <th>Name</th>
            <th>Quantity</th>
          </tr>
          </thead>
          <tbody>
          {delivery?.items.map(item => (
            <tr>
              <th>{item.name}</th>
              <td>{item.quantity}</td>
            </tr>
          ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default All
