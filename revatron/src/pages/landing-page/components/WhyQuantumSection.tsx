import { motion } from "framer-motion";
import { useTranslation } from "react-i18next";

type QuantumCardProps = {
  tittle: string;
  content: string;
  image?: string;
};

const WhyQuantumCard = ({ tittle, content }: QuantumCardProps) => {
  return (
    <div className='flex justify-between border-b border-warm-200 py-20 max-md:flex-col max-md:gap-4 max-md:py-6'>
      <h1 className='text-4xl font-medium text-warm-800 max-lg:text-xl'>{tittle}</h1>
      <p className='max-w-[560px] text-xl font-normal text-gray-500 max-lg:text-lg'>{content}</p>
    </div>
  );
};

function WhyQuantumSection() {
  const { t } = useTranslation();
  return (
    <section className='mt-24 w-full max-w-[1296px] px-4 max-lg:mt-12'>
      {/* Tittle */}
      <div className='flex justify-between max-md:flex-col'>
        <motion.h1
          initial='hidden'
          whileInView='visible'
          viewport={{ once: true, amount: 0.4 }}
          transition={{ delay: 0, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
          variants={{
            hidden: { opacity: 0, y: 50 },
            visible: { opacity: 1, y: 0 },
          }}
          className='max-w-[696px] text-4xl font-bold text-warm-800 max-lg:text-2xl'>
          {t("landing.communication_tittle")}
        </motion.h1>
        <motion.p
          initial='hidden'
          whileInView='visible'
          viewport={{ once: true, amount: 0.4 }}
          transition={{ delay: 0.4, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
          variants={{
            hidden: { opacity: 0, y: 0 },
            visible: { opacity: 1, y: 0 },
          }}
          className='max-w-[560px] text-base font-normal text-warm-500'>
          {t("landing.communication_content")}
        </motion.p>
      </div>
      {/* Repeated Section */}
      <motion.div
        initial='hidden'
        whileInView='visible'
        viewport={{ once: true, amount: 0 }}
        transition={{ delay: 0.6, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
        variants={{
          hidden: { opacity: 0, y: 50 },
          visible: { opacity: 1, y: 0 },
        }}
        className='flex flex-col'>
        <WhyQuantumCard tittle={t("landing.communication_card_1_tittle")} content={t("landing.communication_card_1_content")} />
        <WhyQuantumCard tittle={t("landing.communication_card_2_tittle")} content={t("landing.communication_card_2_content")} />

        <WhyQuantumCard tittle={t("landing.communication_card_3_tittle")} content={t("landing.communication_card_3_content")} />
        <WhyQuantumCard tittle={t("landing.communication_card_4_tittle")} content={t("landing.communication_card_4_content")} />
        <WhyQuantumCard tittle={t("landing.communication_card_5_tittle")} content={t("landing.communication_card_5_content")} />
      </motion.div>
    </section>
  );
}

export default WhyQuantumSection;
