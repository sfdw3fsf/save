import Carousel from "@/components/carousel/Carousel";
import { motion } from "framer-motion";
import { useTranslation } from "react-i18next";

const carouselImages = [
  "/images/carousel/slide_1.png",
  "/images/carousel/slide_2.png",
  "/images/carousel/slide_3.jpg",
  "/images/carousel/slide_4.jpg",
];

function AboutUsSection() {
  const { t } = useTranslation();
  return (
    <div className='px-18 pt-24'>
      {/* Title */}
      <motion.div
        initial='hidden'
        whileInView='visible'
        viewport={{ once: true, amount: 0.4 }}
        transition={{ duration: 2, ease: [0.42, 0, 0.58, 1] }}
        variants={{
          hidden: { opacity: 0, y: 0 },
          visible: { opacity: 1, y: 0 },
        }}
        className='flex flex-col gap-2'>
        {/* about us*/}
        <span className='text-base font-medium text-primary-700'>
          <span>✨</span> about us
        </span>
        {/*Title  */}
        <motion.div
          initial='hidden'
          whileInView='visible'
          viewport={{ once: true, amount: 0.4 }}
          transition={{ duration: 2, ease: [0.42, 0, 0.58, 1] }}
          variants={{
            hidden: { opacity: 0, y: 0 },
            visible: { opacity: 1, y: 0 },
          }}
          className='flex gap-10 max-lg:flex-col'>
          <h1 className='max-w-[696px] text-2xl font-bold text-warm-800 lg:text-4xl'>{t("landing.about_us_tittle")}</h1>
          <p className='max-w-[560px] text-base font-normal text-warm-500'>{t("landing.about_us_content")}</p>
        </motion.div>
      </motion.div>
      {/* Carousel*/}
      <div className='mt-24 max-md:mt-4'>
        <Carousel slideImages={carouselImages}></Carousel>
      </div>
    </div>
  );
}

export default AboutUsSection;
