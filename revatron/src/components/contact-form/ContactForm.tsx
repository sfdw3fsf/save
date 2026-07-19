import { useTranslation } from "react-i18next";

function FormFieldInput({ label }: { label: string }) {
  return (
    <div className='flex h-fit w-full max-w-[300px] gap-2 border-b border-warm-200 py-2 max-xl:max-w-full'>
      <label className='whitespace-nowrap text-xl font-semibold text-warm-25'>{label}</label>
      <input type='text' className='w-full bg-transparent text-lg text-warm-25 placeholder-warm-200 outline-none' />
    </div>
  );
}

function ContactForm() {
  const { t } = useTranslation();

  return (
    <div className='flex flex-col rounded-lg px-4 py-24 max-xl:py-12 md:px-10 xl:flex-row'>
      {/* Image Part */}
      <div className='bg-warm-850 relative flex max-h-[648px] w-full flex-col gap-6 overflow-hidden p-3 xl:w-[512px] xl:p-10'>
        <img
          src='/images/about-us/contact-background.png'
          alt='Background'
          className='absolute inset-0 -z-10 h-full w-full object-cover max-xl:rounded-t-xl xl:rounded-l-xl'
        />
        <h1 className='text-4xl font-bold text-warm-25 max-xl:text-3xl'>Contact us</h1>
        <p className='w-full max-w-[371px] text-xl font-normal text-warm-25 max-xl:text-base'>{t("form.tittle")}</p>
      </div>

      {/* Form */}
      <form className='flex w-full max-w-[704px] flex-col items-center justify-end gap-10 rounded-r-xl bg-warm-950 p-10 max-xl:-mt-0.5 max-xl:rounded-xl max-xl:rounded-t-none max-xl:p-3 xl:-ml-0.5 xl:max-h-[648px]'>
        <div className='flex w-full flex-wrap gap-2 max-xl:gap-10'>
          <FormFieldInput label={t("form.first_name")} />
          <FormFieldInput label={t("form.last_name")} />
        </div>

        <div className='flex w-full gap-2 max-xl:flex-wrap max-xl:gap-10'>
          <FormFieldInput label={t("form.company")} />
          <FormFieldInput label={t("form.business_email")} />
        </div>

        <div className='flex w-full flex-col gap-2 border-b border-warm-200 py-2'>
          <label className='whitespace-nowrap text-xl font-semibold text-warm-25'>{t("form.inquiry_details")}</label>
          <textarea className='h-[180px] w-full resize-none bg-transparent text-lg text-warm-25 placeholder-warm-200 outline-none'></textarea>
        </div>
        <div className='flex flex-col gap-6'>
          <button
            type='submit'
            className='flex w-full max-w-[300px] items-center justify-center rounded bg-primary-500 px-6 py-3 text-white max-xl:max-w-full'>
            Send
          </button>
          <p className='text-sm font-normal text-warm-25'>{t("form.policy")}</p>
        </div>
      </form>
    </div>
  );
}

export default ContactForm;
