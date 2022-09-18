import '../styles/globals.css'
import type {AppProps} from 'next/app'
import Layout from "../components/layout";
import { createClient, Provider } from 'urql';

const client = createClient({
  url: 'http://localhost/api/graphql',
});

function MyApp({Component, pageProps}: AppProps) {
  return (
    <Layout>
      <Provider value={client}>
        <Component {...pageProps} />
      </Provider>
    </Layout>
  )
}

export default MyApp
