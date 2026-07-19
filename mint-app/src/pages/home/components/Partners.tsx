import { getPartnersField, getTestimonialsField } from '@/data/home/home-api';
import i18n from '@/i18n';
import { useQuery } from '@tanstack/react-query';
import { useTranslation } from 'react-i18next';
type PartnerItemProps = {
  image: string;
};
type PartnerDetailItemProps = {
  image: string;
  comment: string;
  name: string;
  role: string;
};

const PartnerItem = ({ image }: PartnerItemProps) => {
  return (
    <div
      className='partner-item md:max-w-[160px] min-w-[160px] h-[66px] rounded-xl
      text-center
    inline-block py-[18px] px-6 mx-3 my-2 slide-animation hover:shadow-xl bg-white shadow-md border-[rgba(16, 24, 40, 0.06)] justify-center items-center'>
      <img src={image} alt='Partner logo' loading='lazy' className='max-h-11 min-h-11 object-cover m-auto' />
    </div>
  );
};

const PartnerDetailItem = ({ comment, image, name, role }: PartnerDetailItemProps) => {
  return (
    <div
      className='md:h-[358px] md:w-[715px] w-80 min-w-80 p-6 h-fit border rounded-3xl bg-white  border-primary-200 shadow-primary-200
    shadow-sm hover:shadow-xl hover:shadow-primary-200 inline-block m-3 break-words whitespace-normal text-left'>
      <div className='flex flex-col items-start'>
        <img src={image} className='max-w-14 min-w-14 max-h-14 min-h-14 object-contain justify-start ' />
        <div className='flex-1'>
          <p className='text-base font-normal text-slate-800 min-h-40 max-h-40 h-40 block whitespace-pre-line'>{comment}</p>
          <p className='text-base font-bold text-slate-950 line-clamp-1 whitespace-pre-line sm:mt-0 mt-4'>{name}</p>
          <p className='text-md font-normal text-slate-500 line-clamp-2 whitespace-pre-line'>{role}</p>
        </div>
      </div>
    </div>
  );
};

const Partners = () => {
  const { t: tCommon } = useTranslation('common');
  const { data: PartnersField } = useQuery({
    queryKey: ['getPartners'],
    queryFn: () =>
      getPartnersField({
        size: 100,
      }),
  });

  const { data: TestimonialFields } = useQuery({
    queryKey: ['getTestimonial'],
    queryFn: () =>
      getTestimonialsField({
        size: 100,
      }),
  });

  return (
    <div className='lg:py-24 lg:pb-12 py-12 pb-6 w-full flex flex-col gap-16 overflow-clip'>
      <div>
        <h2 className='text-primary-600 text-sm text-center mb-4 font-medium'>{tCommon('home.partners')}</h2>
        <p className='text-slate-800 md:text-4xl text-3xl text-center font-bold max-w-[768px] md:px-0 px-5 mx-auto mb-16 whitespace-pre-line'>
          {tCommon('home.partners_title')}
        </p>
      </div>
      <div className='overflow-hidden whitespace-nowrap w-full carousel'>
        {Array(3)
          .fill(null)
          .map(
            (_, index) =>
              Array.isArray(PartnersField?.data) && (
                <div key={index} className='inline-block max-w-max slide-animation'>
                  {[...PartnersField.data, ...PartnersField.data].map((item, index) => (
                    <PartnerItem key={index} image={item.url} />
                  ))}
                </div>
              ),
          )}
      </div>
      <div className='mt-16'>
        <h2 className='text-primary-600 text-sm text-center mb-3 font-medium'>Clients</h2>
        <p className='text-slate-800 md:text-4xl text-3xl text-center font-bold max-w-[768px] md:px-0 px-5 mx-auto mb-9 whitespace-pre-line'>
          {tCommon('home.testimonial')}
        </p>
      </div>

      <div className='overflow-hidden whitespace-nowrap w-full carousel'>
        {Array.isArray(TestimonialFields?.data) && (
          <div className='inline-block max-w-max slide-animation'>
            {[...TestimonialFields.data, ...TestimonialFields.data].map((item, index) => (
              <PartnerDetailItem
                key={index}
                image={item.url}
                comment={i18n.language === 'en' ? item.commentInEnglish : item.commentInKorean}
                name={i18n.language === 'en' ? item.nameInEnglish : item.nameInKorean}
                role={i18n.language === 'en' ? item.roleInEnglish : item.roleInKorean}
              />
            ))}
          </div>
        )}
        {Array.isArray(TestimonialFields?.data) && (
          <div className='inline-block max-w-max h-fit slide-animation pb-6'>
            {[...TestimonialFields.data, ...TestimonialFields.data].map((item, index) => (
              <PartnerDetailItem
                key={index}
                image={item.url}
                comment={i18n.language === 'en' ? item.commentInEnglish : item.commentInKorean}
                name={i18n.language === 'en' ? item.nameInEnglish : item.nameInKorean}
                role={i18n.language === 'en' ? item.roleInEnglish : item.roleInKorean}
              />
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default Partners;
