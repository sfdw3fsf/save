import ContactForm from "@/components/contact-form/ContactForm";
import IntroductionSection from "@/components/introduction/IntroductionSection";
import PageContainer from "@/components/layout/PageContainer";
import { useTranslation } from "react-i18next";

function EmulatedPage() {
  const { t } = useTranslation();
  const tittleImage = "/images/tittle/emulated-quantum.png";
  return (
    <PageContainer isTittleImage tittle={t("emulated.page_tittle")} image={tittleImage}>
      {/* Tittle */}
      <div className='max-w-[686px] px-4 py-10 text-center text-lg font-normal text-warm-600'>{t("emulated.tittle")}</div>
      <IntroductionSection
        tittle_one={t("emulated.card_1_tittle")}
        content_one={t("emulated.card_1_content")}
        image_one={"/images/emulated/emulated_1.png"}
        tittle_two={t("emulated.card_2_tittle")}
        content_two={t("emulated.card_2_content")}
        image_two={"/images/emulated/emulated_2.png"}
      />
      <ContactForm />
    </PageContainer>
  );
}

export default EmulatedPage;
