import { advisoryFieldCategoriesAPI, businessFieldCategoriesAPI, medicalFieldCategoriesAPI } from '@/data/account/account.api';
import {
  transformAdvisoryFieldCategoryResponse,
  transformBusinessFieldCategoryResponse,
  transformMedicalFieldCategoryResponse,
} from '@/data/account/account.service';
import { AdvisoryFieldCategoryRESP } from '@/data/account/response/advisory-field-category.response';
import { BusinessFieldCategoryRESP } from '@/data/account/response/business-field-category.response';
import { MedicalFieldCategoryRESP } from '@/data/account/response/medical-field-category.response';
import { BaseResponse } from '@/types/response.type';
import { QUERY_KEYS } from '@/utils/constants/data.constant';
import { useQuery } from '@tanstack/react-query';
import { useMemo } from 'react';
import { ProjectBusinessCombineInput } from '../business-field-register/ProjectRequestBusinessModal';
import { filterCategorySecondary } from '../helpers/project-request-div.helpers';

export const useCombineFields = (businessCombineInput?: ProjectBusinessCombineInput) => {
  // API Requests
  const { data: businessFieldCategories = [], isFetching: isFetchingBusiness } = useQuery({
    queryKey: [QUERY_KEYS.BUSINESS_FIELD_CATEGORIES],
    queryFn: businessFieldCategoriesAPI,
    select: ({ data }: BaseResponse<BusinessFieldCategoryRESP[]>) => transformBusinessFieldCategoryResponse(data),
  });

  const { data: advisoryFieldCategories = [], isFetching: isFetchingAdvisory } = useQuery({
    queryKey: [QUERY_KEYS.ADVISORY_FIELD_CATEGORIES],
    queryFn: advisoryFieldCategoriesAPI,
    select: ({ data }: BaseResponse<AdvisoryFieldCategoryRESP[]>) => transformAdvisoryFieldCategoryResponse(data),
  });

  const { data: medicalFieldCategories = [], isFetching: isFetchingMedical } = useQuery({
    queryKey: [QUERY_KEYS.MEDICAL_FIELD_CATEGORIES],
    queryFn: medicalFieldCategoriesAPI,
    select: ({ data }: BaseResponse<MedicalFieldCategoryRESP[]>) => transformMedicalFieldCategoryResponse(data),
  });

  // Filtered Fields
  const filteredBusinessField = useMemo(
    () => filterCategorySecondary(businessFieldCategories, businessCombineInput?.businessFieldIds, 'businessField', ' , '),
    [businessFieldCategories, businessCombineInput?.businessFieldIds],
  );

  const filteredAdvisoryField = useMemo(
    () => filterCategorySecondary(advisoryFieldCategories, businessCombineInput?.advisoryFieldIds, 'advisoryField', ' , '),
    [advisoryFieldCategories, businessCombineInput?.advisoryFieldIds],
  );

  const filteredMedicalField = useMemo(
    () => filterCategorySecondary(medicalFieldCategories, businessCombineInput?.medicalFieldIds, 'medicalField', ' , '),
    [medicalFieldCategories, businessCombineInput?.medicalFieldIds],
  );

  return {
    businessFieldCategories,
    advisoryFieldCategories,
    medicalFieldCategories,
    isFetchingBusiness,
    isFetchingAdvisory,
    isFetchingMedical,
    filteredBusinessField,
    filteredAdvisoryField,
    filteredMedicalField,
  };
};
