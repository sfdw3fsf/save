import ExploreArrowIcon from "@/components/icon/ExploreArrowIcon";
import { motion } from "framer-motion";
import { useTranslation } from "react-i18next";

const ImpactCards = ({ tittle, image }: { tittle: string; image: string; link: string }) => {
  return (
    <div className='relative h-[480px] w-[642px] rounded-xl border border-warm-300 p-6 max-md:h-[243px] max-md:w-full'>
      <div className='flex h-full flex-col justify-between'>
        <h1 className='text-3xl font-bold text-warm-950'>{tittle}</h1>
        <a className='flex w-fit items-center justify-center gap-1 border-b border-warm-500 text-base font-normal text-warm-500'>
          Explore more
          <span>
            <ExploreArrowIcon className='text-warm-500' />
          </span>
        </a>
      </div>

      <img src={image} alt='Image' className='-z-1 absolute inset-0 h-full w-full object-contain' />
    </div>
  );
};

function TheImpactSection() {
  const { t } = useTranslation();

  const impactData = [
    {
      tittle: t("landing.impact_card_1_tittle"),
      image: "/images/landing-page/impact_1.png",
      link: "",
    },
    {
      tittle: t("landing.impact_card_2_tittle"),
      image: "/images/landing-page/impact_2.png",
      link: "",
    },
  ];

  return (
    <div className='flex w-full items-center justify-center gap-24 px-4 py-24 max-md:py-12'>
      <div className='w-full max-w-[1296px]'>
        {/* Tittle */}
        <div className='flex w-full justify-between max-lg:flex-col max-lg:gap-4'>
          <motion.h1
            initial='hidden'
            whileInView='visible'
            viewport={{ once: true, amount: 0.4 }}
            transition={{ delay: 0, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
            variants={{
              hidden: { opacity: 0, y: 50 },
              visible: { opacity: 1, y: 0 },
            }}
            className='text-4xl font-bold text-warm-800'>
            {t("landing.impact_tittle")}
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
            {t("landing.impact_content")}
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
          className='flex gap-3 pt-24 max-md:flex-col max-md:pt-10'>
          {impactData.map((item, index) => (
            <ImpactCards key={index} tittle={item.tittle} image={item.image} link={item.link} />
          ))}
        </motion.div>
      </div>
    </div>
  );
}

export default TheImpactSection;
