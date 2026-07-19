import { ReactNode } from "react";
import Footer from "./Footer";

type Props = {
  children: ReactNode;
  isTittleImage?: boolean;
  tittle?: string;
  image?: string;
};
function PageContainer({ children, isTittleImage = false, tittle, image }: Props) {
  return (
    <div className='mt-[92px] flex h-full min-h-[calc(100vh-92px)] w-full flex-col items-center'>
      {isTittleImage && (
        <section
          className='relative flex h-[352px] w-full items-center justify-center max-md:h-[304px]'
          style={{
            backgroundImage: ` url(${image})`,
            backgroundPosition: "center",
            backgroundRepeat: "no-repeat",
            backgroundSize: "cover",
          }}>
          {/* <div className='absolute inset-0 rounded-lg bg-gradient-to-b from-[rgba(0,0,0,0)] from-0% via-[rgba(0,0,0,0.88)] via-75% to-[rgba(0,0,0,0.92)] to-100%' /> */}
          <p className='text-center text-5xl font-bold text-white max-md:text-2xl'>{tittle}</p>
        </section>
      )}
      {children}
      <Footer />
    </div>
  );
}

export default PageContainer;
