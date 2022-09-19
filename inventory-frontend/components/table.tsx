import {DeliveriesQuery, DeliveryCollection} from "./queries.generated";
import Link from "next/link";

export default function Table(input: DeliveriesQuery) {
  const deliveries = input.deliveries?.content
  return (
    <>
      <div className="overflow-x-auto">
        <table className="table w-full">
          <thead>
          <tr>
            <th>Delivery ID</th>
            <th>Category</th>
            <th>Status</th>
            <th>Planned Delivery Date</th>
            <th>Actual Delivery Date</th>
            <th>Item Types</th>
            <th>Item Count</th>
            <th></th>
          </tr>
          </thead>
          <tbody>
          {deliveries?.map(delivery => (
            <tr>
              <th>{delivery.id}</th>
              <td>{delivery.category}</td>
              <td>{delivery.status}</td>
              <td>{delivery.plannedDeliveryDate}</td>
              <td>{delivery.actualDeliveryDate}</td>
              <td>{delivery.items.length}</td>
              <td>{delivery.items.reduce((total,item) => total+item.quantity, 0)}</td>
              <td>
                <button className="btn btn-neutral btn-sm">
                  <Link href={"/deliveries/" + delivery.id}>View</Link>
                </button>
              </td>
            </tr>
          ))}
          </tbody>
        </table>
      </div>
    </>
  )
}
