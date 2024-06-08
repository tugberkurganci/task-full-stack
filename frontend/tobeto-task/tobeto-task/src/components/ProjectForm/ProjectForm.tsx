import React, { useState } from "react";
import { Formik, Form, FormikHelpers } from "formik";
import * as Yup from "yup";
import axiosInstance from "../../utils/axiosInterceptors";
import { ProjectRequest, ProjectResponse } from "../../models/models";
import FormikInput from "../../components/FormikInput/FormikInput";
import "./projectForm.css"; // Custom CSS file
import { Card } from "react-bootstrap";

interface ProjectFormProps {
  setIsAdded: (isAdded: boolean) => void;
}

const ProjectForm: React.FC<ProjectFormProps> = ({ setIsAdded }) => {
  const [initialValues, setInitialValues] = useState<ProjectRequest>({
    projectName: "",
    startDate: "",
    endDate: "",
  });

  const validationSchema = Yup.object({
    projectName: Yup.string().required("Proje adı zorunludur"),
    startDate: Yup.date().required("Başlangıç tarihi zorunludur"),
    endDate: Yup.date().required("Bitiş tarihi zorunludur"),
  });

  const handleProjectSubmit: (
    values: ProjectRequest,
    helpers: FormikHelpers<ProjectRequest>
  ) => Promise<void> = async (values, { setSubmitting, resetForm }) => {
    try {
      await axiosInstance.post<ProjectResponse>("/projects", values);
      setIsAdded(true);
      resetForm(); // Formu sıfırla
      setInitialValues({ // initialValues'ı güncelle
        projectName: "",
        startDate: "",
        endDate: "",
      });
    } catch (error) {
      console.error("Proje eklenirken hata oluştu:", error);
    } finally {
      setSubmitting(false);
    }
  };

  const onChangeInput = (handleChange: any, e: any, values: any) => {
    handleChange(e);
    setInitialValues({ ...values, [e.target.name]: e.target.value });
  };
  return (
    <div className="project-form-container">
      <Card>
        <Card.Body>
          <Card.Title>Proje Ekle</Card.Title>
          <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={handleProjectSubmit}
          >
            {({ isSubmitting, values, handleChange }) => (
              <Form>
                <FormikInput
                  value={initialValues.projectName}
                  label="Proje Adı"
                  name="projectName"
                  type="text"
                  onChange={(e: any) => {
                    onChangeInput(handleChange, e, values);
                  }}
                />
                <FormikInput
                  value={initialValues.startDate}
                  label="Başlangıç Tarihi"
                  name="startDate"
                  type="date"
                  onChange={(e: any) => {
                    onChangeInput(handleChange, e, values);
                  }}
                />
                <FormikInput
                  value={initialValues.endDate}
                  label="Bitiş Tarihi"
                  name="endDate"
                  type="date"
                  onChange={(e: any) => {
                    onChangeInput(handleChange, e, values);
                  }}
                />
                <button type="submit" className="btn btn-primary mt-2" disabled={isSubmitting}>
                  Proje Ekle
                </button>
              </Form>
            )}
          </Formik>
        </Card.Body>
      </Card>
    </div>
  );
};

export default ProjectForm;

