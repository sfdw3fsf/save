import ContactForm from "@/components/contact-form/ContactForm";
import IntroductionSection from "@/components/introduction/IntroductionSection";
import PageContainer from "@/components/layout/PageContainer";
import { useTranslation } from "react-i18next";

function TheImpactPage() {
  const { t } = useTranslation();
  const tittleImage = "/images/tittle/the-impact.png";
  return (
    <PageContainer isTittleImage tittle={t("the_impact.page_tittle")} image={tittleImage}>
      <IntroductionSection
        tittle_one={t("the_impact.card_1_tittle")}
        content_one={t("the_impact.card_1_content")}
        image_one={"/images/the-impact/impact_1.png"}
        tittle_two={t("the_impact.card_2_tittle")}
        content_two={t("the_impact.card_2_content")}
        image_two={"/images/the-impact/impact_2.png"}
      />
      <ContactForm />
    </PageContainer>
  );
}

export default TheImpactPage;
