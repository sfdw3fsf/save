import ArrowRightIcon from "@/components/icon/ArrowRightIcon";
import { Trans, useTranslation } from "react-i18next";

function Coporate() {
  const { t } = useTranslation();
  return (
    <div className='flex h-full w-full items-center justify-center py-24 max-lg:py-12'>
      <div className='flex h-full max-h-[420px] w-full max-w-[1280px] flex-col items-center justify-center gap-10 rounded-lg bg-warm-12 py-14'>
        {/*  Tittle*/}
        <div className='flex flex-col items-center gap-4'>
          <p className='text-center text-4xl font-bold text-white max-lg:text-2xl'>
            <Trans i18nKey={"landing.coporate_tittle"}>
              Want to
              <span
                // className='bg-clip-text text-primary-500 text-transparent'
                style={{
                  backgroundImage: `
              radial-gradient(circle,
                #F2AE30 0%,
                #0A6ABF 23%,
                #F27127 48%,
                #F2AE30 68%,
                #F27127 86%,
                #163A59 100%)`,
                  WebkitBackgroundClip: "text",
                  WebkitTextFillColor: "transparent",
                }}>
                Corporate
              </span>
              with us?
            </Trans>
          </p>
          <p className='text-4xl font-bold text-white max-lg:text-2xl'>{t("landing.coporate_subtittle")}</p>
        </div>
        {/* Button */}
        <button className='flex w-full max-w-[240px] transform items-center justify-center gap-2 rounded-lg bg-feat-30 px-4 py-3 font-bold text-text-36 transition duration-300 ease-in-out hover:scale-105 hover:shadow-xl'>
          <span>Contact Us</span>
          <span>
            <ArrowRightIcon />
          </span>
        </button>
        <div></div>
      </div>
    </div>
  );
}

export default Coporate;
