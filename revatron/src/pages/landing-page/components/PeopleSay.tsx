import QuoteIcon from "@/components/icon/QuoteIcon";
import { motion } from "framer-motion";
import { useTranslation } from "react-i18next";

const PeopleSayCard = ({ tittle, content }: { tittle: string; content: string }) => {
  return (
    <div className='flex min-w-[320px] max-w-[416px] flex-col items-center gap-6 rounded-lg border border-warm-200 p-6 max-md:h-[402px]'>
      <div className='flex flex-col gap-6'>
        <QuoteIcon />
        <p className='h-[192px] text-base font-normal text-warm-600 max-xl:text-sm'>{content}</p>
      </div>
      <h1 className='text-2xl font-bold text-warm-800 max-xl:text-xl'>{tittle}</h1>
    </div>
  );
};

function PeopleSay() {
  const { t } = useTranslation();
  const peopleSayData = [
    {
      tittle: t("landing.people_card_1_tittle"),
      content: t("landing.people_card_1_content"),
    },
    {
      tittle: t("landing.people_card_2_tittle"),
      content: t("landing.people_card_2_content"),
    },
    {
      tittle: t("landing.people_card_3_tittle"),
      content: t("landing.people_card_3_content"),
    },
  ];

  return (
    <div className='flex w-full items-center justify-center px-4 py-24 max-lg:py-12'>
      <div className='flex w-full max-w-[1296px] flex-col'>
        {/* Tittle */}
        <div className='flex flex-col gap-2'>
          <motion.p
            initial='hidden'
            whileInView='visible'
            viewport={{ once: true, amount: 0.4 }}
            transition={{ delay: 0, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
            variants={{
              hidden: { opacity: 0, y: 50 },
              visible: { opacity: 1, y: 0 },
            }}
            className='text-base font-medium text-primary-58'>
            {t("landing.people_subtittle")}
          </motion.p>
          <motion.h1
            initial='hidden'
            whileInView='visible'
            viewport={{ once: true, amount: 0.4 }}
            transition={{ delay: 0.4, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
            variants={{
              hidden: { opacity: 0, y: 50 },
              visible: { opacity: 1, y: 0 },
            }}
            className='text-4xl font-bold text-warm-800 max-lg:text-2xl'>
            {t("landing.people_tittle")}
          </motion.h1>
        </div>
        {/* Peple Say About Us */}
        <motion.div
          initial='hidden'
          whileInView='visible'
          viewport={{ once: true, amount: 0.4 }}
          transition={{ delay: 1, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
          variants={{
            hidden: { opacity: 0, y: 0 },
            visible: { opacity: 1, y: 0 },
          }}
          className='mt-16 flex w-full gap-6 overflow-x-auto max-lg:mt-4'>
          {peopleSayData.map((item, index) => (
            <PeopleSayCard key={index} tittle={item.tittle} content={item.content} />
          ))}
        </motion.div>
      </div>
    </div>
  );
}

export default PeopleSay;
