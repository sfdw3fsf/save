import PageContainer from "@/components/layout/PageContainer";
import { useTranslation } from "react-i18next";

function ContactPage() {
  const { t } = useTranslation();

  const tittleImage = "/images/tittle/about-us.png";
  return (
    <PageContainer isTittleImage tittle='Contact Us' image={tittleImage}>
      {/* Tittle */}
      <div className='py-10 text-center text-xl font-normal text-warm-600'>{t("contact.tittle")}</div>
      <ContactForm />
    </PageContainer>
  );
}

export default ContactPage;

function FormFieldInput({ label }: { label: string }) {
  return (
    <div className='flex h-fit w-full gap-2 border-b border-warm-200 py-2 max-xl:max-w-full'>
      <label className='whitespace-nowrap text-3xl font-semibold text-warm-25 max-md:text-xl'>{label}</label>
      <input type='text' className='w-full bg-transparent text-lg text-warm-25 placeholder-warm-200 outline-none' />
    </div>
  );
}

function ContactForm() {
  const { t } = useTranslation();

  return (
    <div className='flex flex-col rounded-lg px-4 pb-24 md:px-10 xl:flex-row'>
      {/* Form */}
      <form className='flex w-full max-w-[1216px] items-center justify-end gap-14 rounded-xl bg-warm-950 p-10 max-md:flex-col'>
        <div className='flex w-full flex-col flex-wrap gap-28'>
          <FormFieldInput label={t("form.first_name")} />
          <FormFieldInput label={t("form.last_name")} />
          <FormFieldInput label={t("form.company")} />
          <FormFieldInput label={t("form.business_email")} />
        </div>

        <div className='flex w-full flex-col gap-14'>
          <div className='flex w-full flex-col gap-2 border-b border-warm-200 py-2'>
            <label className='whitespace-nowrap text-3xl font-semibold text-warm-25 max-md:text-xl'>
              {t("form.inquiry_details")}
            </label>
            <textarea className='h-[340px] w-full resize-none bg-transparent text-lg text-warm-25 placeholder-warm-200 outline-none max-md:h-[200px]'></textarea>
          </div>
          <div className='flex flex-col gap-6'>
            <button
              type='submit'
              className='flex w-full max-w-[300px] items-center justify-center rounded bg-primary-500 px-6 py-3 text-white max-xl:max-w-full'>
              Send
            </button>
            <p className='text-sm font-normal text-warm-25'>{t("form.policy")}</p>
          </div>
        </div>
      </form>
    </div>
  );
}
