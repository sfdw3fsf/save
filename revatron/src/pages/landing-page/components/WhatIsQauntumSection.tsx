import EntanglementIcon from "@/components/icon/EntaglementIcon";
import QuantumBitIcon from "@/components/icon/QauntumBitIcon";
import QdkIcon from "@/components/icon/QkdIcon";
import TheoremIcon from "@/components/icon/TheoremIcon";
import { motion } from "framer-motion";
import { ReactNode } from "react";
import { useTranslation } from "react-i18next";

type QuantumCardProps = {
  tittle: string;
  content: string;
  icon: ReactNode;
};

function QuantumCard({ tittle, content, icon }: QuantumCardProps) {
  return (
    <div className='flex flex-col gap-3 rounded-xl border border-warm-8 border-opacity-10 p-6 lg:w-[315px]'>
      <span className='size-8 shrink-0'>{icon}</span>
      <div className='flex flex-col gap-2'>
        <h1 className='text-base font-semibold text-warm-7'>{tittle}</h1>
        <p className='text-sm font-normal text-warm-300'>{content}</p>
      </div>
    </div>
  );
}

function WhatIsQauntumSection() {
  const { t } = useTranslation();
  return (
    <section className='mt-24 flex w-full flex-col items-center justify-center gap-4 bg-warm-950 px-4 py-24 max-lg:mt-12 max-lg:py-12 lg:gap-24'>
      {/* Title */}
      <div className='flex w-full max-w-[1296px] justify-between max-md:flex-col max-md:gap-4'>
        <motion.h1
          initial='hidden'
          whileInView='visible'
          viewport={{ once: true, amount: 0.4 }}
          transition={{ delay: 0, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
          variants={{
            hidden: { opacity: 0, y: 50 },
            visible: { opacity: 1, y: 0 },
          }}
          className='text-2xl font-bold text-warm-25 lg:text-4xl'>
          {t("landing.quantum_tittle")}
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
          className='max-w-[560px] text-base font-normal text-warm-100'>
          {t("landing.quantum_content")}{" "}
        </motion.p>
      </div>
      {/* Card */}
      <motion.div
        initial='hidden'
        whileInView='visible'
        viewport={{ once: true, amount: 0.4 }}
        transition={{ delay: 0.6, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
        variants={{
          hidden: { opacity: 0, y: 50 },
          visible: { opacity: 1, y: 0 },
        }}
        className='flex w-full max-w-[1290px] gap-3 overflow-auto max-lg:flex-col'>
        <QuantumCard
          tittle={t("landing.quantum_card_1_tittle")}
          content={t("landing.quantum_card_1_content")}
          icon={<QuantumBitIcon />}
        />
        <QuantumCard
          tittle={t("landing.quantum_card_2_tittle")}
          content={t("landing.quantum_card_2_content")}
          icon={<EntanglementIcon />}
        />
        <QuantumCard
          tittle={t("landing.quantum_card_3_tittle")}
          content={t("landing.quantum_card_3_content")}
          icon={<QdkIcon />}
        />
        <QuantumCard
          tittle={t("landing.quantum_card_4_tittle")}
          content={t("landing.quantum_card_4_content")}
          icon={<TheoremIcon />}
        />
      </motion.div>
    </section>
  );
}

export default WhatIsQauntumSection;
