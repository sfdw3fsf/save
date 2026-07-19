import Carousel from "@/components/carousel/Carousel";
import ContactForm from "@/components/contact-form/ContactForm";
import PageContainer from "@/components/layout/PageContainer";
import { Trans, useTranslation } from "react-i18next";

const carouselImages = [
  "/images/carousel/slide_1.png",
  "/images/carousel/slide_2.png",
  "/images/carousel/slide_3.jpg",
  "/images/carousel/slide_4.jpg",
];

function AboutUsPage() {
  const { t } = useTranslation();

  const tittleImage = "/images/tittle/about-us.png";
  return (
    <PageContainer isTittleImage tittle={t("about_us.page_tittle")} image={tittleImage}>
      <div className='flex w-full flex-col items-center justify-center px-4'>
        {/* Tittle */}
        <div className='max-w-[686px] py-10 text-center text-3xl font-semibold text-warm-800 max-md:text-left max-md:text-2xl'>
          {/* <p>{t("about_us.tittle")}</p> */}
          {
            <Trans i18nKey={"about_us.tittle"}>
              Revatron is a team of
              <span className='before:-z-1 relative z-10 before:absolute before:left-0 before:top-2/3 before:h-4 before:w-full before:bg-[#E1F7E3] before:mix-blend-multiply max-md:before:w-0'>
                accomplished scientists
              </span>
              and
              <span className='text-primary-500'>business developers</span>
            </Trans>
          }
        </div>
        {/* Carousel */}
        <div className='py-12'>
          <Carousel slideImages={carouselImages}></Carousel>
        </div>
      </div>
      {/* Contact Form */}
      <ContactForm />
    </PageContainer>
  );
}

export default AboutUsPage;
