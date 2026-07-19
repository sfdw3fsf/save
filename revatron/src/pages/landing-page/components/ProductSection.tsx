import ExploreArrowIcon from "@/components/icon/ExploreArrowIcon";
import { motion } from "framer-motion";
import { useTranslation } from "react-i18next";

const ProductCards = ({ tittle, image }: { tittle: string; image: string; link: string }) => {
  return (
    <div className='relative h-[480px] w-[642px] rounded-xl border border-warm-800 p-6 max-md:h-[243px] max-md:w-full'>
      <div className='flex h-full flex-col justify-between'>
        <h1 className='text-3xl font-bold text-warm-25'>
          {" "}
          {tittle.split("\n").map((line, index) => (
            <span key={index}>
              {line}
              <br />
            </span>
          ))}
        </h1>
        <a className='flex w-fit items-center justify-center gap-1 border-b border-warm-7 text-base font-normal text-warm-7'>
          Explore more{" "}
          <span>
            <ExploreArrowIcon />
          </span>
        </a>
      </div>
      <img src={image} alt='Image' className='-z-1 absolute inset-0 h-full w-full object-contain' />
    </div>
  );
};

function ProductSection() {
  const { t } = useTranslation();

  const productData = [
    {
      tittle: t("landing.product_card_1_tittle"),
      image: "images/landing-page/product_1.png",
      link: "",
    },
    {
      tittle: t("landing.product_card_2_tittle"),
      image: "images/landing-page/product_2.png",
      link: "",
    },
  ];

  return (
    <div className='flex w-full items-center justify-center bg-warm-950 px-4 py-24 max-lg:py-12'>
      <div className='w-full max-w-[1296px]'>
        {/* Tittle */}

        <motion.h1
          initial='hidden'
          whileInView='visible'
          viewport={{ once: true, amount: 0.4 }}
          transition={{ delay: 0, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
          variants={{
            hidden: { opacity: 0, y: 50 },
            visible: { opacity: 1, y: 0 },
          }}
          className='text-4xl font-bold text-warm-25 max-md:text-2xl'>
          {t("landing.product_tittle")}
        </motion.h1>

        {/* Card */}
        <motion.div
          initial='hidden'
          whileInView='visible'
          viewport={{ once: true, amount: 0.4 }}
          transition={{ delay: 0.8, duration: 1.3, ease: [0.42, 0, 0.58, 1] }}
          variants={{
            hidden: { opacity: 0, y: 0 },
            visible: { opacity: 1, y: 0 },
          }}
          className='mt-10 flex gap-3 max-md:flex-col'>
          {productData.map((item, index) => (
            <ProductCards key={index} tittle={item.tittle} image={item.image} link={item.link} />
          ))}
        </motion.div>
      </div>
    </div>
  );
}

export default ProductSection;
