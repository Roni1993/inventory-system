import {Dispatch, SetStateAction} from "react";
import Link from "next/link";

export type Page = {
  page: number,
  totalPages: number,
  setPage: Dispatch<SetStateAction<number>>
}
export default function Pagination(page: Page) {

  return (
    <>
      <div className="w-full flex justify-center mb-4">
        <div className="btn-group">
          {[...Array(page.totalPages)].map((_,index) => (
            <button onClick={() => page.setPage(index)}
                    className={`btn ${index==page.page && "btn-active"}`}
            >{index+1}</button>
          ))}
        </div>
      </div>
    </>
  )
}
