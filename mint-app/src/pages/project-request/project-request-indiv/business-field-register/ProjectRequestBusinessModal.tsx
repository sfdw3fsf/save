import Modal from '@/components/core/modal/Modal';
import { FieldCategoryDTO } from '@/data/account/dto/field-category.dto';
import { BusinessCombineInput, BusinessInput } from '@/pages/sign-up-expert/components/SignUpExpertBusinessModal';

import { FormOptionInputValue } from '@/components/core/form/form-option/FormOption';
import i18n, { I18nLanguage } from '@/i18n';
import { ForwardedRef, forwardRef, useEffect, useImperativeHandle, useMemo, useRef, useState } from 'react';
import { FieldValues, UseControllerProps } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { useFieldSchema } from '../hooks/useFieldSchema';
import ProjectRequestAdvisoryFieldForm from './ProjectRequestAdvisoryFieldForm';
import ProjectRequestBusinessFieldForm from './ProjectRequestBusinessFieldForm';
import ProjectRequestBusinessModalTitle from './ProjectRequestBusinessModalTitle';
import ProjectRequestMedicalFieldForm from './ProjectRequestMedicalFieldForm';

const TOTAL_STEPS = 3;

const BUSINESS_FIELD_FORM_ID = 'business-field-form';
const MAIN_ADVISORY_FORM_ID = 'main-advisory-form';
const MEDICAL_FIELD_FORM_ID = 'medical-field-form';

export type ProjectBusinessCombineInput = BusinessCombineInput & {
  medicalFieldIds: BusinessInput;
};

export type ProjectRequestBusinessFormProps<T extends FieldValues> = Pick<UseControllerProps<T>, 'control'> & {
  id: string;
  className?: string;
  baseData: FieldCategoryDTO[];
  showErrorMessage?: boolean;
  onSubmit?: () => void;
  onExternalChange?: (key: string, value: FormOptionInputValue) => void;
  language?: I18nLanguage;
};

export type ProjectRequestBusinessModalRef = {
  open: boolean;
  setOpen: (open: boolean) => void;
};
type ProjectRequestBusinessModalProps = {
  onSubmit: (data: ProjectBusinessCombineInput) => void;
  businessFieldCategories?: FieldCategoryDTO[];
  advisoryFieldCategories?: FieldCategoryDTO[];
  medicalFieldCategories?: FieldCategoryDTO[];
  defaultValues?: ProjectBusinessCombineInput;
};
const ProjectRequestBusinessModal = forwardRef<ProjectRequestBusinessModalRef, ProjectRequestBusinessModalProps>(
  (
    {
      onSubmit,
      businessFieldCategories = [],
      advisoryFieldCategories = [],
      medicalFieldCategories = [],
      defaultValues,
    }: ProjectRequestBusinessModalProps,
    ref: ForwardedRef<ProjectRequestBusinessModalRef | null>,
  ): JSX.Element => {
    const { t: tCommon } = useTranslation('common');
    const language = i18n.language as I18nLanguage;

    // REFS
    const bodyRef = useRef<HTMLDivElement | null>(null);

    // STATES
    const [open, setOpen] = useState(false);
    const [currentStep, setCurrentStep] = useState<number>(1);

    // HOOKS
    useImperativeHandle(ref, () => ({ open, setOpen }));

    // FN
    const onClose = () => {
      businessFieldMethods.reset({ ...businessFieldObjectSchema.getDefault() });
      setOpen(false);
    };

    // CONST
    const titleProps = useMemo(() => {
      const shared = { currentStep: currentStep, totalSteps: TOTAL_STEPS };
      switch (currentStep) {
        case 1:
          return {
            ...shared,
            title: tCommon('project_request.business_field_step.title'),
            description: tCommon('project_request.business_field_step.description'),
          };
        case 2:
          return {
            ...shared,
            title: tCommon('project_request.keys_area.title'),
            description: tCommon('project_request.keys_area.description'),
          };
        case 3:
          return {
            ...shared,
            title: tCommon('project_request.medical_field.title'),
            description: tCommon('project_request.medical_field.description'),
          };
        default:
          break;
      }
    }, [currentStep, tCommon]);

    const { businessFieldMethods, advisoryFieldMethods, medicalFieldMethods, businessFieldObjectSchema } = useFieldSchema({
      businessFieldCategories,
      advisoryFieldCategories,
      medicalFieldCategories,
    });

    useEffect(() => {
      if (!open) {
        setCurrentStep(1);
      } else {
        businessFieldMethods.reset(defaultValues?.businessFieldIds);
        advisoryFieldMethods.reset(defaultValues?.advisoryFieldIds);
        medicalFieldMethods.reset(defaultValues?.medicalFieldIds);
      }
    }, [
      advisoryFieldMethods,
      businessFieldMethods,
      defaultValues?.advisoryFieldIds,
      defaultValues?.businessFieldIds,
      defaultValues?.medicalFieldIds,
      medicalFieldMethods,
      open,
    ]);

    useEffect(() => {
      bodyRef.current?.scrollTo({ top: 0, behavior: 'smooth' });
    }, [currentStep]);
    return (
      <Modal
        fullHeight
        open={open}
        onClose={onClose}
        isDivider={false}
        header={titleProps && <ProjectRequestBusinessModalTitle {...titleProps} />}
        bodyRef={bodyRef}
        buttons={[
          {
            // show on step 1
            children: tCommon('component.cancel'),
            onClick: onClose,
            className: currentStep === 1 ? undefined : 'hidden',
          },

          // show on step 2 & 3
          {
            children: tCommon('component.previous'),
            className: currentStep !== 1 ? undefined : 'hidden',
            onClick: () => setCurrentStep((prev) => prev - 1),
          },

          // show on step 1 & 2
          {
            children: tCommon('component.next'),
            variant: 'primary',
            className: currentStep !== 3 ? undefined : 'hidden',
            form: currentStep === 1 ? BUSINESS_FIELD_FORM_ID : MAIN_ADVISORY_FORM_ID,
          },

          // show on step 3
          {
            children: tCommon('component.save'),
            variant: 'primary',
            className: currentStep === 3 ? undefined : 'hidden',
            form: MEDICAL_FIELD_FORM_ID,
          },
        ]}
        body={
          <>
            <ProjectRequestBusinessFieldForm
              id={BUSINESS_FIELD_FORM_ID}
              className={currentStep === 1 ? undefined : 'hidden'}
              baseData={businessFieldCategories}
              control={businessFieldMethods.control}
              showErrorMessage={!!Object.keys(businessFieldMethods.formState.errors).length}
              onSubmit={businessFieldMethods.handleSubmit(() => {
                setCurrentStep((prev) => prev + 1);
              })}
              onExternalChange={(key: string, value: FormOptionInputValue) => {
                businessFieldMethods.reset({ ...businessFieldObjectSchema.getDefault(), [key]: value });
              }}
              language={language}
            />
            <ProjectRequestAdvisoryFieldForm
              id={MAIN_ADVISORY_FORM_ID}
              className={currentStep === 2 ? undefined : 'hidden'}
              baseData={advisoryFieldCategories}
              control={advisoryFieldMethods.control}
              showErrorMessage={!!Object.keys(advisoryFieldMethods.formState.errors).length}
              onSubmit={advisoryFieldMethods.handleSubmit(() => {
                setCurrentStep((prev) => prev + 1);
              })}
              language={language}
            />
            <ProjectRequestMedicalFieldForm
              id={MEDICAL_FIELD_FORM_ID}
              className={currentStep === 3 ? undefined : 'hidden'}
              baseData={medicalFieldCategories}
              control={medicalFieldMethods.control}
              showErrorMessage={!!Object.keys(medicalFieldMethods.formState.errors).length}
              onSubmit={medicalFieldMethods.handleSubmit((data: BusinessInput) => {
                onSubmit({
                  businessFieldIds: businessFieldMethods.watch(),
                  advisoryFieldIds: advisoryFieldMethods.watch(),
                  medicalFieldIds: data,
                });
                onClose();
              })}
              language={language}
            />
          </>
        }
      />
    );
  },
);

ProjectRequestBusinessModal.displayName = 'ProjectRequestBusinessModal';

export default ProjectRequestBusinessModal;
