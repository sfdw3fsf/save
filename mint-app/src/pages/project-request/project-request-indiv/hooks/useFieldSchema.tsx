import { FieldCategoryDTO } from '@/data/account/dto/field-category.dto';
import {
  projectAdvisoryFieldSchema,
  projectBusinessFieldSchema,
  projectMedicalFieldSchema,
} from '@/utils/schemas/project-request.schema';
import { yupResolver } from '@hookform/resolvers/yup';
import { useMemo } from 'react';
import { useForm } from 'react-hook-form';

type Props = {
  businessFieldCategories?: FieldCategoryDTO[];
  advisoryFieldCategories?: FieldCategoryDTO[];
  medicalFieldCategories?: FieldCategoryDTO[];
};
export const useFieldSchema = ({
  businessFieldCategories = [],
  advisoryFieldCategories = [],
  medicalFieldCategories = [],
}: Props) => {
  // BUSINESS FIELD FORM
  const businessFieldObjectSchema = useMemo(() => projectBusinessFieldSchema(businessFieldCategories), [businessFieldCategories]);
  const businessFieldMethods = useForm({
    resolver: yupResolver(businessFieldObjectSchema),
    defaultValues: businessFieldObjectSchema.getDefault(),
  });

  // MAIN ADVISORY FORM
  const advisoryFieldObjectSchema = useMemo(() => projectAdvisoryFieldSchema(advisoryFieldCategories), [advisoryFieldCategories]);
  const advisoryFieldMethods = useForm({
    resolver: yupResolver(advisoryFieldObjectSchema),
    defaultValues: advisoryFieldObjectSchema.getDefault(),
  });

  // MEDICAL FIELD FORM
  const medicalFieldObjectSchema = useMemo(() => projectMedicalFieldSchema(medicalFieldCategories), [medicalFieldCategories]);
  const medicalFieldMethods = useForm({
    resolver: yupResolver(medicalFieldObjectSchema),
    defaultValues: medicalFieldObjectSchema.getDefault(),
  });

  return {
    businessFieldMethods,
    advisoryFieldMethods,
    medicalFieldMethods,
    businessFieldObjectSchema,
  };
};
