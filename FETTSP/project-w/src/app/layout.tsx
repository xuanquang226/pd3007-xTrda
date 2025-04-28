import type { Metadata } from "next";
import { Inter } from "next/font/google";
import 'bootstrap/dist/css/bootstrap.min.css';
import Header from "@/components/header";
import Footer from "@/components/footer";
import Sidebar from "@/components/sidebar";
import Container from "react-bootstrap/Container";
import 'react-toastify/dist/ReactToastify.css';
import "./globals.css";
import styles from "./page.module.css";
import { Children, Suspense } from "react";
import TopNav from "@/components/top-navigation";
const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Xiaotrada",
  description: "Created by Quang",
  icons: {
    icon: [
      {url: '/images/favicon.ico', type: 'image/x-icon'},
      {url: '/images/favicon.png', type: 'image/png', sizes: '32x32'}
    ],
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className={inter.className}>
        {/* <Header /> */}
        <div className={styles.layout}>
          <Sidebar></Sidebar>
          <main className={styles.mainContent}>
            <TopNav></TopNav>
            <Suspense fallback={<div>Loading...</div>}>
              {children}
            </Suspense>
          </main>
        </div>
        {/* <Footer /> */}
      </body>
    </html>
  );
}
