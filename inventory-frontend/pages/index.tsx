import type {NextPage} from 'next'
import Head from 'next/head'
import Image from 'next/image'
import styles from '../styles/Home.module.css'
import Link from "next/link";

const Home: NextPage = () => {
  return (
    <div className="hero">
      <div className="hero-content text-center">
        <div className="max-w-md">
          <h1 className="text-5xl font-bold">Welcome to the Store Dashboard</h1>
          <p className="py-6">You can look at the tomorrows deliveries, Delivered goods and all deliveries if needed</p>
          <Link href="/deliveries/tomorrow"><button className="btn btn-neutral">Get Started</button></Link>
        </div>
      </div>
    </div>
  )
}

export default Home
