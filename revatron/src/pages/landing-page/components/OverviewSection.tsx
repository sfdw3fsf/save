import { motion } from "framer-motion";
import { useTranslation } from "react-i18next";
import LandingDemo from "/images/landingDemo.png";

function OverviewSection() {
  const { t } = useTranslation();
  return (
    <div
      className='flex h-[560px] w-full max-w-[1376px] items-center justify-center xl:h-[768px]'
      style={{
        backgroundImage: `url(${LandingDemo})`,
        backgroundSize: "cover",
        backgroundPosition: "center ",
        backgroundRepeat: "no-repeat",
      }}>
      <div className='flex w-full flex-col items-center justify-center gap-3'>
        <motion.h1
          initial='hidden'
          whileInView='visible'
          viewport={{ once: true, amount: 0 }}
          transition={{ duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
          variants={{
            hidden: { opacity: 0, y: 50 },
            visible: { opacity: 1, y: 0 },
          }}
          className='max-w-[768px] text-center text-4xl font-bold text-warm-950 max-md:text-2xl'>
          {t("overview_tittle")}
        </motion.h1>
        <motion.p
          initial='hidden'
          whileInView='visible'
          viewport={{ once: true, amount: 0 }}
          transition={{ delay: 0.3, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
          variants={{
            hidden: { opacity: 0, y: 50 },
            visible: { opacity: 1, y: 0 },
          }}
          className='text-center text-lg font-normal text-warm-600 max-md:text-base'>
          {t("overview_content")}
        </motion.p>
      </div>
    </div>
  );
}

export default OverviewSection;
