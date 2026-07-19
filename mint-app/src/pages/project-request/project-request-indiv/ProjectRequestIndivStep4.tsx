import Badge from '@/components/core/badge/Badge';
import FormField from '@/components/core/form/form-field/FormField';
import Spinner from '@/components/core/spinner/Spinner';
import { privaciesAPI } from '@/data/privacy/privacy.api';
import { termsAPI } from '@/data/term/term.api';
import i18n, { I18nLanguage } from '@/i18n';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { agreedTermsSchema, ProjectRequestStep4Input } from '@/utils/schemas/project-request.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { ProjectRequestIndivProps } from './ProjectRequestIndivPage';
import InfoCard from './components/InfoCard';
import InfoList from './components/InfoList';

const ProjectRequestIndivStep4Page = ({ formID, onNext, initialValues, isEdit }: ProjectRequestIndivProps) => {
  const { t: tCommon } = useTranslation('common');
  const { t: tForm } = useTranslation('form');
  const language = i18n.language as I18nLanguage;

  // APIS
  const { data: terms, isFetching: isFetchingTerms } = useQuery({
    queryKey: [QUERY_KEYS.TERMS],
    queryFn: () => termsAPI({ type: 'REQUEST_PROJECT' }),
  });

  const { data: privacyPolicies, isFetching: isFetchingPrivacyPolicies } = useQuery({
    queryKey: [QUERY_KEYS.PRIVACIES],
    queryFn: () => privaciesAPI({ type: 'REQUEST_PROJECT' }),
  });

  // TERMS FORM
  const {
    control,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<ProjectRequestStep4Input>({
    resolver: yupResolver<ProjectRequestStep4Input>(agreedTermsSchema),
    defaultValues: agreedTermsSchema.getDefault(),
  });

  // METHODS
  const onSubmit = handleSubmit(async (data) => {
    onNext?.(data);
  });

  // EFFECTS
  useEffect(() => {
    if (initialValues) {
      reset(initialValues);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [initialValues, isEdit]);

  if (isFetchingTerms || isFetchingPrivacyPolicies) return <Spinner raw className='size-10 self-center my-10' />;

  return (
    <div className='flex flex-col'>
      <div className='font-medium text-md'>{tCommon('project_request.step_4.title')}</div>
      <div className='text-slate-500 mt-1 mb-5'>{tCommon('project_request.step_4.description')}</div>
      <form id={formID} className='flex flex-col gap-4 mb-1' onSubmit={onSubmit}>
        <div className='flex flex-col gap-4'>
          <InfoCard
            title={tCommon('project_request.term_of_use')}
            isCollapse
            isError={!!errors.agreedTerm}
            message={tForm('required.term_of_use')}
            leftSection={
              <FormField
                type='option'
                hideErrorMessage
                componentProps={{
                  control: control,
                  name: 'agreedTerm',
                  className: 'items-center gap-4',
                  required: true,
                  type: 'checkbox',
                  label: <Badge className='h-[22px] min-w-10' label={tCommon('component.required')} color='orange' />,
                }}
              />
            }>
            {terms?.data?.map((term, index) => (
              <div className='flex flex-col w-full gap-4 items-center justify-start my-4' key={index}>
                <InfoList
                  title={language === 'ko' ? term.nameInKorean : term.nameInEnglish}
                  content={language === 'ko' ? term.descriptionInKorean : term.descriptionInEnglish}
                />
              </div>
            ))}
          </InfoCard>
          <InfoCard
            title={tCommon('project_request.privacy')}
            isCollapse
            isError={!!errors.agreedPolicy}
            message={tForm('required.policy')}
            leftSection={
              <FormField
                type='option'
                hideErrorMessage
                componentProps={{
                  control: control,
                  name: 'agreedPolicy',
                  className: 'items-center gap-4',
                  required: true,
                  type: 'checkbox',
                  label: <Badge className='h-[22px] min-w-10' label={tCommon('component.required')} color='orange' />,
                }}
              />
            }>
            {privacyPolicies?.data?.map((privacy, index) => (
              <div className='flex flex-col w-full gap-4 items-center justify-start my-4' key={index}>
                <InfoList
                  title={language === 'ko' ? privacy.nameInKorean : privacy.nameInEnglish}
                  content={language === 'ko' ? privacy.descriptionInKorean : privacy.descriptionInEnglish}
                />
              </div>
            ))}
          </InfoCard>
        </div>
      </form>
    </div>
  );
};

export default ProjectRequestIndivStep4Page;
